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
                .addField(">help", "Displays this help message.", false)
                .addField(">hello", "Says hello to you.", false)
                .addField(">ping", "Displays bot latency.", false)
                .addField(">admes (query)", "Ask anything to the bot.", false)
                .addField(">dt", "Shows the current date and time (AM/PM).", false)
                .addField(">gsearch (search text)", "Searches google and returns results as html.", false)
                .addField(">makefile (text)", "Creates file from text.", false)
                .addField(">calc (num1),(sign),(num2) _Warning: No spaces after/before/in commas_", "Does calculation. Supported signs -> +, -, *, /", false)
                .addField(">reply (text),(reply) _Warning: No spaces after/before/in commas_", "Makes the bot reply when you send a specific text.", false)
                .addField(">noreply (text)", "Disables custom reply.", false)
                .addField(">replies", "Displays all custom replies set.", false)
                .setColor(Color.LIGHT_GRAY)
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
