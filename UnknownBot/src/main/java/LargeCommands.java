import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LargeCommands {

    public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
    public static Map<String, String> customReplies = new HashMap<>();

    public static void admes(MessageReceivedEvent event) {
        try {
            CommandHandler.sendMessage(event, "Info: Bot will be halted until it replies to you.");
            String text = event.getMessage().getContentRaw();
            String asked = text.substring(7);
            System.out.println("'" + event.getAuthor().getName() + "'" + " asked in " + "'" + event.getGuild().getName()
                    + "'" + ": " + asked);
            @SuppressWarnings("resource")
			Scanner scn = new Scanner(System.in);
            System.out.println("Reply: ");
            if (scn.hasNextLine()) {
                String text2 = scn.nextLine();
                CommandHandler.sendMessage(event, "Reply: " + text2);
            } else {
                CommandHandler.sendMessage(event, "Reply: " + "...");
            }
        } catch (StringIndexOutOfBoundsException ex) {
            CommandHandler.sendMessage(event,
                    "Please ask something after typing '>admes' and " + "put space between command and asking.");
        }
    }

    public static void gsearch(@NotNull MessageReceivedEvent event) {
        try {
            String search = URLEncoder.encode(event.getMessage().getContentRaw().substring(9), StandardCharsets.UTF_8);
            String searchURL = GOOGLE_SEARCH_URL + "?q=" + search + "&num=" + 10;
            System.out.println("SEARCH: " + searchURL);
            Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
            File file = new File("C:\\Users\\Arpan\\OneDrive\\Desktop\\Bot Files\\draft.html");
            file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(doc.html());
            writer.close();
            CommandHandler.sendFile(event, file);
        } catch (StringIndexOutOfBoundsException ex) {
            CommandHandler.sendMessage(event,
                    "Please type search text after typing '>gsearch' and " + "put space between command and text.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            CommandHandler.sendMessage(event, "Error. Please report the dev about this (UnknownPro 56).");
        }
    }

    public static void makefile(@NotNull MessageReceivedEvent event) {
        try {
            String text = event.getMessage().getContentRaw().substring(10);
            File file = new File("C:\\Users\\Arpan\\OneDrive\\Desktop\\Bot Files\\text.txt");
            file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
            CommandHandler.sendFile(event, file);
        } catch (StringIndexOutOfBoundsException ex) {
            CommandHandler.sendMessage(event,
                    "Please type text after typing '>makefile' and " + "put space between command and text.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calc(MessageReceivedEvent event) {
        String[] text = event.getMessage().getContentRaw().split(",");
        ArrayList<String> argsList = new ArrayList<>(Arrays.asList(text));
        String num = argsList.get(0).substring(6);
        argsList.set(0, num);
        System.out.println("Args: " + argsList);
        try {
            switch (argsList.get(1)) {
                case ("+") -> {
                    float num1 = Float.parseFloat(argsList.get(0));
                    float num2 = Float.parseFloat(argsList.get(2));
                    float result = num1 + num2;
                    CommandHandler.sendMessage(event, num1 + " " + argsList.get(1) + " " + num2 + " = " + result);
                }
                case ("-") -> {
                    float num1 = Float.parseFloat(argsList.get(0));
                    float num2 = Float.parseFloat(argsList.get(2));
                    float result = num1 - num2;
                    CommandHandler.sendMessage(event, num1 + " " + argsList.get(1) + " " + num2 + " = " + result);
                }
                case ("*") -> {
                    float num1 = Float.parseFloat(argsList.get(0));
                    float num2 = Float.parseFloat(argsList.get(2));
                    float result = num1 * num2;
                    CommandHandler.sendMessage(event, num1 + " " + argsList.get(1) + " " + num2 + " = " + result);
                }
                case ("/") -> {
                    float num1 = Float.parseFloat(argsList.get(0));
                    float num2 = Float.parseFloat(argsList.get(2));
                    float result = num1 / num2;
                    CommandHandler.sendMessage(event, num1 + " " + argsList.get(1) + " " + num2 + " = " + result);
                }
                default -> CommandHandler.sendMessage(event, "Not a valid operation symbol. Valid ones are +, -, * and /.");
            }
        } catch (NumberFormatException ex) {
            CommandHandler.sendMessage(event, "Please provide a number, as '>calc (num1),(+ or - or * or /),(num2)'.");
        } catch (IndexOutOfBoundsException ex) {
            CommandHandler.sendMessage(event, "Incorrect number of arguments given!");
        } catch (Exception ex) {
            CommandHandler.sendMessage(event, "Unknown Error Occured! Please report this to the developer (UnknownPro56).\n" + 
            "Error: " + ex.getMessage());
        }
    }

    public static void setCustomReply(MessageReceivedEvent event) {
        try {
            String text = event.getMessage().getContentRaw();
            String[] args = text.split(",");
            ArrayList<String> argsList = new ArrayList<>();
            for (String string : args) {
                argsList.add(string);
            }
            argsList.set(0, argsList.get(0).replace(">reply ", ""));
            System.out.println("Args: " + argsList);
            if (!argsList.get(0).isEmpty() && !argsList.get(1).isEmpty()) {
                customReplies.put(argsList.get(0), argsList.get(1));
                CommandHandler.sendMessage(event, "Successfully set custom reply! Bot will now reply with '" 
                + customReplies.get(argsList.get(0)) + "' when any message contains '" + argsList.get(0) + "'.");
                
                // Write to file
                File arrayFile = new File("C:\\Users\\Arpan\\OneDrive\\Desktop\\Bot Files\\replyArray.data");
            	try {
            		arrayFile.delete();
            		arrayFile.createNewFile();
            		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(arrayFile))) {
            			objectOutputStream.writeObject(customReplies);
            		}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            	// Done
            	
            	
            } else {
                CommandHandler.sendMessage(event, "No arguments given! Correct syntax - >reply (text),(reply) _No spaces between commas and arguments!");
            }
        } catch (IndexOutOfBoundsException ex) {
            CommandHandler.sendMessage(event, "Incorrect arguments given. Correct syntax - >reply (text),(reply) _No spaces between commas and arguments!");
        }
    }

    public static void customReply(MessageReceivedEvent event, String reply) {
        CommandHandler.sendMessage(event, reply);
    }

    public static void noReply(MessageReceivedEvent event) {
        for (String text : customReplies.keySet()) {
            if (event.getMessage().getContentRaw().contains(text)) {
                customReplies.remove(text);
                CommandHandler.sendMessage(event, "Successfully disabled custom reply " + text + "!");
                return;
            }
        }
        CommandHandler.sendMessage(event, "No reply named '" + event.getMessage().getContentRaw().substring(9) + "' was found!");
    }
}
