# saenkaGame Oyunu

## Gereksinimler

### Sistem Gereksinimleri
- JDK 17 veya üzeri
- Gradle 8.0 veya üzeri
- En az 4GB RAM
- 500MB boş disk alanı

### Kütüphaneler ve Bağımlılıklar
- JavaFX 17
- Gradle Wrapper
- JUnit 5 (test için)

## Kurulum

1. Projeyi klonlayın:
```bash
git clone https://github.com/MrEnderez/saenkaGame.git
cd saenkaGame
```

2. Gradle ile bağımlılıkları yükleyin:
```bash
./gradlew build
```

## Çalıştırma

Oyunu başlatmak için aşağıdaki komutlardan birini kullanabilirsiniz:

```bash
./gradlew run
```

veya

```bash
java -jar build/libs/saenkaGame.jar
```

## Geliştirme Ortamı Kurulumu

1. IntelliJ IDEA, Eclipse veya VS Code gibi bir IDE kurun
2. JDK 17'yi yükleyin
3. IDE'de projeyi açın
4. Gradle sync işlemini gerçekleştirin

## Oyun Kontrolleri

- W/A/S/D veya Yön Tuşları: Karakteri hareket ettirme
- SPACE: Zıplama
- ESC: Oyun menüsü
- ENTER: Seçim yapma

## Hata Giderme

Eğer oyun başlatılırken hata alırsanız:

1. JDK sürümünüzün 17 veya üzeri olduğundan emin olun
2. Gradle cache'ini temizleyin:
```bash
./gradlew clean
```
3. Projeyi yeniden derleyin:
```bash
./gradlew build
```

## İletişim

Sorun veya önerileriniz için:
- GitHub Issues
- E-posta: [atillaytkn0816@gmail.com]
