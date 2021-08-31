import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LargeCommandHandler {

    public static void handleCommand(MessageReceivedEvent event) {
        String text = event.getMessage().getContentRaw();
        if (text.contains(">admes")) LargeCommands.admes(event);
        else if (text.contains(">gsearch")) LargeCommands.gsearch(event);
        else if (text.contains(">makefile")) LargeCommands.makefile(event);
        else if (text.contains(">calc")) LargeCommands.calc(event);
        else if (text.contains(">reply")) LargeCommands.setCustomReply(event);
        else if (text.contains(">noreply")) LargeCommands.noReply(event);
        else CommandHandler.sendMessage(event, "No such command was found! Type '>help' to view available commands.");
    }
}
