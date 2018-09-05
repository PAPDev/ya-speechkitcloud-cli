# Yandex Spechkit Cloud CLI
### Описание 
Утилита коммандной строки позволяющая делать запросы к облачному сервису yandex, 
для разпознования речи.

### Поддерживаемые форматы аудеоданных

Формат аудиоданных | Медиаконтейнер (формат файла) | Потоковый режим
------------------ | ----------------------------- | ---------------
Медиаконтейнер WAV может содержать аудиоданные любого формата, например PCM | WAV | Нет 
MPEG-1 Audio Layer 3 (MP3) | MP3 | Нет
Speex | .spx | Да
Opus | .opus | Да
Opus | .pcm | Да

### Запуск
1. Создать директорию и перейти в нее 
```
mkdir vr
cd vr
```
2. Клонировать проект из репозитория
```
git clone https://github.com/PAPDev/ya-speechkitcloud-cli.git
```
3. Перейти в папку проекта и переключиться на ветку develop
```
cd ya-speechkitcloud-cli
git checkout develop
```
4. Собрать jar
```
mvn package
```
5. Запустить jar
```
java -jar ./target/speechcloudcli.jar
```