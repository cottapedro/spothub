package br.com.cotta.SpotHub.infrastructure.provider;

import dev.spoocy.genius.GeniusClient;
import dev.spoocy.genius.core.GeniusClientBuilder;
import dev.spoocy.genius.data.Lyrics;
import dev.spoocy.genius.data.Search;
import dev.spoocy.genius.data.SearchSong;
import dev.spoocy.genius.exception.GeniusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class GeniusLyricsProvider {
    private String clientId = "WA8wiSe1ZQNmnmLo2y8Br-YjBGS5G6dUCDgqvZgff2Pud3fXivJYKn3oSlGYM_jX";
    private String clientSecret = "bw4YnK6AsxG2gG9s5DdAeUj6Tm20aJNS4ssQXDvKMioXaGzYuocdcO0j8vUAKGlsPU3Q3bPVR-U55zdO6IrPxg";
    private String authorizationToken = "PlLRdkzES-jHPs-wEY9OcmXpuv7B0IUu-fBzmY3E9095LILvl26BaALqT9CgzTg2";
    private String callbackUrl = "https://localhost";
    public Lyrics findLyricsByArtistAndSong(String musicName, List<String> artists){

        GeniusClient client = new GeniusClientBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setAuthType(GeniusClientBuilder.AuthType.CLIENT_ACCESS_TOKEN)
                .setCallbackUrl(callbackUrl)
                .setUserAgent("Mozilla/5.0")
                .build();

        client.setAuthorizationCode(authorizationToken);

        Search result = client.search().setQuery(musicName).execute();

        SearchSong song = result.getResults()
                .stream()
                .max(Comparator.comparingLong(searchSong ->
                        artists.stream()
                                .filter(searchSong.getArtistNames()::contains)
                                .count()
                ))
                .orElseThrow(() -> new NoSuchElementException("Letra não encontrada para os artistas: " + artists));

        String songUrl = song.getUrl();
        return getLyrics(songUrl, musicName);
    }

    private Lyrics getLyrics(String url, String musicName) {
        try {
            org.jsoup.nodes.Document document = Jsoup
                    .connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();

            document.select("br").append("\\n");
            document.select("p").prepend("\\n\\n");

            List<Element> lyricsElements = document.select("div[data-lyrics-container=true]");

            if (lyricsElements.size() == 0) {
                throw new GeniusException("Could not find Lyrics Container. Check the URL and report this to the developer.");
            }

            String lyrics = "";
            for(Element lyricsElement : lyricsElements) {
                lyrics += Jsoup.clean(lyricsElement.html(), "", Safelist.none(), new org.jsoup.nodes.Document.OutputSettings().prettyPrint(false)).replace("\\n", "\n");
            }

            return new Lyrics(musicName, lyrics);
        } catch (IOException e) {
            throw new GeniusException("Error while getting Song Lyrics.", e);
        }
    }
}
