import java.io.File;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandHandler {
    public static void handleCommand(MessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            String text = event.getMessage().getContentRaw();
            if (event.getMessage().getContentRaw().charAt(0) == '>') {
                System.out.println("Bot was asked: " + event.getMessage().getContentRaw());
                switch (event.getMessage().getContentRaw()) {
                    case ">ping":
                        SmallCommands.ping(event);
                        break;
                    case ">hello":
                        SmallCommands.hello(event);
                        break;
                    case ">dt":
                        SmallCommands.dt(event);
                        break;
                    case ">help":
                        SmallCommands.help(event);
                        break;
                    case ">replies":
                        SmallCommands.replies(event);
                        break;
                    default:
                        LargeCommandHandler.handleCommand(event);
                        break;
                }
            } else {
                if (getReply(text) != null) LargeCommands.customReply(event, getReply(text));
            }
        }
    }

    public static void sendMessage(MessageReceivedEvent event, String message) {
        event.getChannel().sendMessage(message).queue();
    }

    public static void sendFile(MessageReceivedEvent event, File file) {
        event.getChannel().sendFile(file).queue();
    }

    public static String getReply(String text) {
        for (String reply : LargeCommands.customReplies.keySet()) {
            if (text.contains(reply)) return LargeCommands.customReplies.get(reply);
        }
        return null;
    }
}
