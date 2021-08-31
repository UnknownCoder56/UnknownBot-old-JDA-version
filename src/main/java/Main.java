import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {

    public static JDA bot;
    public static void main(String[] args) throws LoginException {
    	initData();
        bot = JDABuilder.createDefault(Secret.getKey())
                .addEventListeners(new Main())
                .setActivity(Activity.listening("your commands (which start with '>')"))
                .build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        CommandHandler.handleCommand(event);
    }
    
    @SuppressWarnings("unchecked")
	public static void initData() {
    	File arrayFile = new File("C:\\Users\\Arpan\\OneDrive\\Desktop\\Bot Files\\replyArray.data");
    	try {
    		arrayFile.createNewFile();
    		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(arrayFile))) {
    			LargeCommands.customReplies = (Map<String, String>) objectInputStream.readObject();
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
