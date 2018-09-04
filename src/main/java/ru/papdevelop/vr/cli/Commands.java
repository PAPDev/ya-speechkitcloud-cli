package ru.papdevelop.vr.cli;


import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.papdevelop.vr.speechcloud.Lang;
import ru.papdevelop.vr.speechcloud.Topic;

import java.io.File;

@Data
@Command(name="yaspechcloudcli",
    version = {"Yandex speechcloud cli 1.0", "Build 1", "(c) 2018"},
    description = "Send FILE to Yandex SpeechKit Cloud for speech recognition",
    mixinStandardHelpOptions = true,
    footer = "Copyright(c) 2018",
    customSynopsis = {"Usage: yaspechcloudcli [-hpV] [-f <path>] -k <key> [-l ru-RU|en-US|uk-UK|tr-TR] -t queries|maps|dates|names|numbers|music|bying"})
public class Commands {

    private static final String LANG_DESCRIPTION =
        "Language for which recognition will be performed, by default ${DEFAULT-VALUE}";
    private static final String KEY_DESCRIPTION = "API-key";
    private static final String TOPIC_DESCRIPTION = "The language model";
    private static final String PROFANITY_DESCRIPTION =
        "option disables the filtering of profanity, by default is ${DEFAULT-VALUE}";
    private static final String FILE_DESCRIPTION =
        "path to file for speech recognition";

    @Option(names = {"-k", "--key"}, description = KEY_DESCRIPTION, required = true)
    private String key;

    @Option(names = {"-t", "--topic"}, description = TOPIC_DESCRIPTION, required = true)
    private Topic topic;

    @Option(names = {"-l", "--lang"}, description = LANG_DESCRIPTION)
    private Lang lang = Lang.RU;

    @Option(names = {"-p", "--profanity"}, description = PROFANITY_DESCRIPTION)
    private boolean profanityDisable = false;

    @Option(names = {"-f", "--file"}, description = FILE_DESCRIPTION, required = false)
    private File path;
}
