# Tiedostonpakkaustyökalu wav-tiedostoille

Tiralabra, loppukesä 2019.

Ohjelmassa on toteutettu kaksi häviötöntä tiedostonpakkausmenetelmää wav-audiotiedostoille (ohjelman tulisi toimia myös muuntyyppisillä tiedostoilla, mutta on kehitetty nimenomaan wav-rakenteen lukemiseen). Toteutetut pakkausmenetelmät ovat [Lempel-Ziw-Welch (LWZ)](https://en.wikipedia.orga/wiki/Lempel–Ziv–Welch) ja [Huffman](https://en.wikipedia.org/wiki/Huffman_coding). Ohjelma toimii yksinkertaisena komentorivityökaluna yleisten unix-komentorivityökalujen konventioita seuraten. 

# Pikakäyttöohje ja komentorivikomennot

Kloonaus:
```
git clone https://github.com/otahontas/javasynth.git
mvn wavcompressor
```

Asennus eli jarin generointi:
```
mvn package
```

Ohjelman ajaminen asennuksen jälkeen:
```
cd target
java -jar wavcompressor.jar
```
*(Saat ohjeet näkyville ajamalla jar-tiedoston ilman parametreja)*

Testit suoritetaan komennolla
```
mvn test
```

Testikattavuusraportti luodaan komennolla
```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella esim.
```
open target/site/jacoco/index.html (MacOS)
xdg-open target/site/jacoco/index.html (Useimmat linux-distrot)
```


# Dokumentaatio
- Käyttöhje
- [Määrittelydokumentti](dokumentaatio/maarittelydokumentti.md)
- Testausdokumentti
- Toteutus- ja suoritituskykydokumentti

# Viikkoraportit
- [Viikko 1](dokumentaatio/raportit/viikko1.md)
- [Viikko 2](dokumentaatio/raportit/viikko2.md)
- [Viikko 3](dokumentaatio/raportit/viikko3.md)
- [Viikko 4](dokumentaatio/raportit/viikko4.md)
- [Viikko 5](dokumentaatio/raportit/viikko5.md)
