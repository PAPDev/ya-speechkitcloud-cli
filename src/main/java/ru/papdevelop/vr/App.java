package ru.papdevelop.vr;

import picocli.CommandLine;
import ru.papdevelop.vr.cli.Commands;
import ru.papdevelop.vr.speechcloud.HttpExecutor;
import ru.papdevelop.vr.speechcloud.Lang;
import ru.papdevelop.vr.speechcloud.Request;
import ru.papdevelop.vr.speechcloud.Topic;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class App {

    public static void main(String[] args) {

        Commands cmd = new Commands();
        CommandLine cl = getPreparedCommandLine(cmd);

        try {
            cl.parse(args);
        } catch (CommandLine.UnmatchedArgumentException | CommandLine.MissingParameterException e) {
            System.out.println(e.getMessage());
            cl.usage(System.out);
            return;
        } catch (CommandLine.ParameterException e) {
            System.out.println(e.getCause().getMessage());
            cl.usage(System.out);
            return;
        }

        if (cl.isUsageHelpRequested()) {
            cl.usage(System.out);
            return;
        } else if (cl.isVersionHelpRequested()) {
            cl.printVersionHelp(System.out);
            return;
        }

        try {
            HttpExecutor http = new HttpExecutor();
            String response = http.execute(
                Request.builder()
                    .uuid(generateUUID())
                    .key(cmd.getKey())
                    .topic(cmd.getTopic())
                    .lang(cmd.getLang())
                    .disableAntimat(cmd.isProfanityDisable())
                    .payload(getFilePayload(cmd.getPath()))
                    .build());
            System.out.println(response);
        } catch (NoSuchFileException e) {
            System.out.println("File not exist: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static byte[] getFilePayload(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static CommandLine getPreparedCommandLine(Commands cmd) {
        CommandLine cl = new CommandLine(cmd);
        cl.registerConverter(Topic.class, t -> {
            Topic topic = Topic.fromString(t);
            if (topic == null) {
                throw new IllegalArgumentException("unexpected topic value: " + t);
            }
            return topic;
        });
        cl.registerConverter(Lang.class, l -> {
            Lang lang = Lang.fromString(l);
            if (lang == null) {
                throw new IllegalArgumentException("unexpected lang value: " + l);
            }
            return lang;
        });
        return cl;
    }
}
