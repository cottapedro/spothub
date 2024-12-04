package br.com.cotta.SpotHub.service;

import dev.spoocy.genius.GeniusClient;
import dev.spoocy.genius.core.GeniusClientBuilder;
import dev.spoocy.genius.data.Lyrics;
import dev.spoocy.genius.data.Search;
import dev.spoocy.genius.data.SearchSong;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GeniusApiService {
    private String clientId = "WA8wiSe1ZQNmnmLo2y8Br-YjBGS5G6dUCDgqvZgff2Pud3fXivJYKn3oSlGYM_jX";
    private String clientSecret = "bw4YnK6AsxG2gG9s5DdAeUj6Tm20aJNS4ssQXDvKMioXaGzYuocdcO0j8vUAKGlsPU3Q3bPVR-U55zdO6IrPxg";
    private String authorizationToken = "PlLRdkzES-jHPs-wEY9OcmXpuv7B0IUu-fBzmY3E9095LILvl26BaALqT9CgzTg2";
    public Lyrics findLyricsByArtistAndSong(String musicName, List<String> artists){

        GeniusClient client = new GeniusClientBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setAuthType(GeniusClientBuilder.AuthType.CLIENT_ACCESS_TOKEN)
                .setCallbackUrl("https://localhost")
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

        return client.lyrics().setSongUrl(songUrl).execute();
    }
}
