import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SmallCommands {

    public static void ping(MessageReceivedEvent event) {
        Main.bot.getRestPing().queue((time) ->
                event.getChannel().sendMessageFormat("Ping: %d ms", time).queue()
        );
    }

    public static void hello(MessageReceivedEvent event) {
        CommandHandler.sendMessage(event, "Hello, " + event.getAuthor().getAsMention() + "!");
    }

    public static void dt(MessageReceivedEvent event) {
        CommandHandler.sendMessage(event, LocalDateTime.now().format(new DateTimeFormatterBuilder().appendPattern("dd MMMM yyyy hh:mm:ss a").toFormatter()));
    }

    public static void help(MessageReceivedEvent event) {
        MessageEmbed embedBuilder = new EmbedBuilder()
                .setTitle("UnknownBot Commands:-")
                .setAuthor("UnknownBot")
                .addField(">help", "Displays this help message.", true)
                .addField(">hello", "Says hello to you.", true)
                .addField(">ping", "Displays bot latency.", true)
                .addField(">admes (query)", "Ask anything to the bot.", true)
                .addField(">dt", "Shows the current date and time (AM/PM).", true)
                .addField(">gsearch (search text)", "Searches google and returns results as html.", true)
                .addField(">makefile (text)", "Creates file from text.", true)
                .addField(">calc (num1),(sign),(num2) _Warning: No spaces after/before/in commas_", "Does calculation. Supported signs -> +, -, *, /", true)
                .addField(">reply (text),(reply) _Warning: No spaces after/before/in commas_", "Makes the bot reply when you send a specific text.", true)
                .addField(">noreply (text)", "Disables custom reply.", true)
                .addField(">replies", "Displays all custom replies set.", true)
                .addField(">clear (amount)", "Clears specified number of messages.", true)
                .setColor(Main.bot.getRoles().get(Main.bot.getRoles().size() - 1).getColor())
                .build();

        event.getChannel().sendMessageEmbeds(embedBuilder).queue();
    }

    public static void replies(MessageReceivedEvent event) {
        StringBuilder repl = new StringBuilder();
        for (String reply : LargeCommands.customReplies.keySet()) {
            repl.append(reply).append(": ").append(LargeCommands.customReplies.get(reply)).append("\n");
        }
        CommandHandler.sendMessage(event, "Currently set custom replies:-\n" + repl);
    }
}
