# Määrittelydokumentti

## Mitä algoritmeja ja tietorakenteita toteutat työssäsi

Tarkoituksena on toteuttaa fast fourier transform (FFT) -algoritmi (Cooley-Tukey), jonka avulla voidaan käsitellä esimerkiksi sawtooth-, square- ja triangle-aaltomuotoja. Lopputuloksena olisi tarkoitus synnyttää hyvin yksinkertainen syntetisaattori, jossa ääntä voidaan FFT:n avulla muokata vaikkapa low-pass ja high-pass -filttereillä. FFT-algoritmi tarvitaan, sillä normaali diskreetti fourier-muunnos on todennäköisesti aaltojen käsittelyyn turhan hidas. 

Lisäksi työssä käytetään todennäköisesti Javan Math-kirjastoa ja mahdollisesti myös hajautustauluja tiedon tehokkaaseen säilömiseen. Näiden toteutukset tehdään siis myös.

## Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit/tietorakenteet

Mikä tahansa jatkuva, riittävän säännöllinen funktio (mm. yllämainitut aaltomuodot) voidaan esittää sinimuotoisten funktioiden integraalina. Fourier-muunnos auttaa erottelemaan sinimuotoiset komponentit, jolloin ääntä voidaan esimerkiksi filtteröidä. 


## Mitä syötteitä ohjelma saa ja miten näitä käytetään

Ohjelmaan tehdään oskillaattori, joka osaa antaa yllä mainittujen aaltomuotojen diskreettejä kuvaajia, jotka ajetaan muunnoksen läpi. Muunnoksen tuote on valmis soitettavaksi Javassa AudioFormat-luokan avulla.

Oskillaattorin tuottamia säveliä vastaavat taajudet tallennetaan hajautustauluun, josta ne haetaan käyttäjän soittaessa syntetisaattoria.

## Tavoitteena olevat aika- ja tilavaativuudet (m.m. O-analyysit)

Cooley-Tukey -algoritmin aikavaativuus on O(n log n), hajautustaulun tilavaativuus O(n) ja operaatiot ovat vakioaikaisia.

## Lähteet
- https://fi.wikipedia.org/wiki/Fourier%E2%80%99n_muunnos#Diskreetti_Fourier'n_muunnos
- https://en.wikipedia.org/wiki/Fast_Fourier_transform
- https://en.wikibooks.org/wiki/Sound_Synthesis_Theory
- https://fi.wikipedia.org/wiki/Oskillaattori
- https://en.wikipedia.org/wiki/Hash_table
