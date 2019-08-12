# Tuntiraportti:
- Yhteensä 15h

# Mitä olen tehnyt tällä viikolla?
- Lukenut edelleen teoriaa, syventänyt audiosignaalikäsittelyyn perehtymistä 
- Refaktoroinut sekalaista koodia, tehnyt siitä järkevästi luettavaa ja lisännyt tarpeelliset javadocit koodikatselmointia ajatellen
- Perehtynyt moniin erilaisiin DFT- ja FFT-implementaatioihin, koodannut ja kokeillut erilaisia implementaatioita
- Perehtynyt javan thread ja runnable -käytänteisiin

# Mitä opin tällä viikolla?
- Ominaisuudet, joita olen miettinyt, onkin muutamat järkevämpi toteuttaa suoraan time domainissa, sillä FFT:tekin on liian hidas
    - Esimerkiksi audion eq-käyrän muokkaajat, esim. low- ja high-pass -filttereistä tulee liian monimutkaisia jos ne tunkkaa FFT:hen, modailee frequencyja ja inverttaa takaisin
- Javalla ei pääse käsiksi käyttäjien syötteisiin reaaliajassa, joten plain tekstikäyttöliittymä ei riitä testaamiseen. Tätä eroa C:hen en ollut tiennyt

# Mikä jäi epäselväksi tai tuottanut vaikeuksia?
- Audiothreadien käynnistäminen ja sulkeminen vähän hankalaa, tähän i/o -puoleen pitää vielä perehtyä
- Oskillaattorin testaaminen, en tajunnut, että Javalla ei pääse kiinni käyttäjien syötteisiin reaaliajassa ilman GUIta. GUI pitänee koodata asap, jotta pääsee käsiksi 
- Projektin skouppi on vielä aika laaja ja olen huomannut, että nappasin projektin vähän liian kaukaa oman osaamisalueen ulkopuolelta

# Mitä teen seuraavaksi?
- GUIn eka versio, jossa keyboardilla soitettava synth
- filtteriominaisuuksia 
