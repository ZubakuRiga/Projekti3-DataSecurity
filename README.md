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

---

## ▶️ Ekzekutimi

Projekti përfshin një klasë `Main.java` që **nis automatikisht** serverin dhe klientin njëkohësisht.