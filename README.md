# Projekti3 - DataSecurity

# Shpërndarja e Çelësit Simetrik duke Përdorur Çelësa të Enkriptuar me RSA – Aplikacion Konsolë

Ky projekt demonstron komunikimin e sigurt midis një klienti dhe një serveri, duke përdorur **RSA** për shpërndarjen e çelësit simetrik dhe **AES** për enkriptimin e mesazheve – gjithçka nëpërmjet një aplikacioni konsolë.

---

## Qëllimi

- Të gjenerohet dhe dërgohet në mënyrë të sigurt një **çelës AES** përmes rrjetit duke përdorur RSA.
- Të përdoret ky çelës për **enkriptimin dhe dekriptimin** e mesazheve midis klientit dhe serverit.

---

## Si funksionon?

1. **Klienti** gjeneron një çift çelësash RSA dhe dërgon çelësin publik te serveri.
2. **Serveri** gjeneron një çelës AES dhe e enkripton me RSA.
3. Klienti e dekripton çelësin AES dhe përdor atë për të enkriptuar një mesazh.
4. Serveri e dekripton mesazhin dhe e shfaq në terminal.


## Hapat për të ekzekutuar projektin

### 1. Kompajllimi i kodit
- Per te kompajlluar serverin, ekzekutoni:
```bash
javac -d out -cp src src/server/KeyDistributionServer.java
```

- Per te kompajlluar klientin, ekzekutoni:
```bash
javac -d out -cp src src/client/ClientKeyDistribution.java
```

### 2. Startoni Serverin për Shpërndarjen e Çelësit

#### Në një terminal, ekzekutoni komandën e mëposhtme për të startuar serverin:

```bash
java -cp out server.KeyDistributionServer
```

#### Në një terminal tjetër
```bash
java -cp out client.ClientKeyDistribution
```

## Shembull Përdorimi
- Pasi kemi startuar serverin kemi kete mesazh:
```text
Duke startuar Serverin për Shpërndarjen e Çelësit...
Duke dëgjuar në portin 5000...

```

- Pasi kemi startuar klientin kemi kete mesazh, ku do na kerkohet te shenojme mesazhin:
```text
Mirë se vini te Shpërndarja e Çelësit Simetrik
U krijua lidhja me serverin per Key Distributin!
RSA public key u dergua te serveri!
Symmetric key u pranua dhe u dekriptua me RSA!
Shkruani mesazhin qe do te enkriptohet
> Mesazhi qe do te enkriptohet
mesazhi u enkriptua me cels simetrik dhe u dergua ne server

```
- Pasi kemi derguar mesazhin, serveri do ta dekriptoje dhe do ta shfaqe ne terminal:
```text
Klienti u lidh.
Çelësi publik RSA i klientit u lexua me sukses.
Çelësi simetrik i enkriptuar u dërgua te klienti.
Mesazhi u dekriptua me sukses:
> Mesazhi qe do te enkriptohet
Serveri u mbyll me sukses.
```