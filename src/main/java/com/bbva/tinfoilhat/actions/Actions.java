package com.bbva.tinfoilhat.actions;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.bbva.tinfoilhat.constants.Constants;
import com.bbva.tinfoilhat.dtos.Task;
import com.bbva.tinfoilhat.dtos.TaskAssign;
import com.bbva.tinfoilhat.dtos.TaskDone;
import com.bbva.tinfoilhat.dtos.User;
import com.bbva.tinfoilhat.rest.RestClientsFactory;

import com.bbva.tinfoilhat.utilities.Utilities;
import javax.inject.Inject;
import com.vdurmont.emoji.EmojiParser;

import org.jboss.logging.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

@ApplicationScoped
public class Actions{

    private static final Logger LOGGER = Logger.getLogger(Actions.class);

    public static final String LOGGIN = "/LOGIN_AS";
    public static final String LOGOFF = "/LOG_OUT";
    public static final String SHOWMETHEMONEY = "/SHOW_ME_THE_MONEY";
    public static final String GETALLTASKS = "/GET_ALL_TASKS";
    public static final String GETMYTASKS = "/GET_MY_TASKS";
    public static final String ASSIGNTASK = "/ASSIGN_TASK";
    public static final String TASKDONE = "/TASK_DONE";

    @Inject
    RestClientsFactory restClientsFactory;

    public List<SendMessage> loginAs (List<String> params, Message messageReceived){
        LOGGER.info("Actions.loginAs.in");

        Long chatId = messageReceived.getChatId();
        SendMessage message = new SendMessage().setChatId(chatId);

        if (Utilities.isEmpty(params) || params.size() != 1){
            LOGGER.info("Actions.loginAs - Empty params");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_WARNING + Constants.WRONG_COMMAND + Constants.EMOJI_WARNING));

            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.LOGGIN_AS_USE));

            return Arrays.asList(message, message2);
        }

        List<User> users = this.restClientsFactory.getUserServicesClient().getUserInfoById(params.get(0));

        if (Utilities.isEmpty(users)){
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_MONKEY + " el usuario no existe"));
            return Arrays.asList(message);

        }

        if (users.size() != 1){
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_MONKEY + Constants.ERROR_MESSAGE));
            return Arrays.asList(message);  
        }

        if (!Utilities.isEmpty(users.get(0).getChatBotID())){
            //Comprobamos si es el mismo
            String chatBotIdSaved = users.get(0).getChatBotID();
            try{
                if (Long.parseLong(chatBotIdSaved) == chatId){
                    LOGGER.info("Actions.loginAs - User already logged");
                    message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_WARNING + 
                                     String.format(Constants.ALREADY_LOGGED_WITH, params.get(0)) + Constants.EMOJI_WARNING ));
                    return Arrays.asList(message);
                }
            }catch(NumberFormatException excp){
            }
        }
        
        this.restClientsFactory.getUserServicesClient().updateUserChatBotId(params.get(0), chatId.toString());
        message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_GOOD));

        LOGGER.info("Actions.loginAs.out");
        return Arrays.asList(message);
    }


    public List<SendMessage> logOff (Message messageReceived){
        LOGGER.info("Actions.logOff.in");

        Long chatId = messageReceived.getChatId();
        SendMessage message = new SendMessage().setChatId(chatId);
      
        List<User> users = this.restClientsFactory.getUserServicesClient().getUserInfoByChatId(chatId.toString());

        if (Utilities.isEmpty(users)){
            LOGGER.error("Actions.logOff - No se pudo encontrar el usuario");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_WARNING + Constants.NO_LOGGED + Constants.EMOJI_WARNING));
            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.TRY_TO_LOGGIN + Constants.EMOJI_BULB));
            return Arrays.asList(message, message2);
        }
        for (User user : users)
            this.restClientsFactory.getUserServicesClient().updateUserChatBotId(user.getId(), "");

        message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_GOOD));
        LOGGER.info("Actions.logOff.out");
        return Arrays.asList(message);
    }


   public List<SendMessage> showMeTheMoney (Message messageReceived){
        LOGGER.info("Actions.showMeTheMoney.in");

        Long chatId = messageReceived.getChatId();
        SendMessage message = new SendMessage().setChatId(chatId);

        List<User> users = this.restClientsFactory.getUserServicesClient().getUserInfoByChatId(chatId.toString());
        if (Utilities.isEmpty(users) || users.size() != 1){
            LOGGER.error("Actions.showMeTheMoney - No se pudo encontrar el usuario");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_MONKEY + Constants.ERROR_MESSAGE));
            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.TRY_TO_LOGGIN + Constants.EMOJI_BULB));
            return Arrays.asList(message, message2);
        }


        Integer amount = Utilities.isEmpty(users.get(0).getTotalPoint())?0:users.get(0).getTotalPoint();
        message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_MONEY + 
                                                   String.format(Constants.TOTAL_POINTS, amount) + 
                                                   Constants.EMOJI_MONEY));
        
        LOGGER.info("Actions.showMeTheMoney.out");
        return Arrays.asList(message);
   } 


    public List<SendMessage> getAllTasks (Message messageReceived, Boolean myTasks){
        LOGGER.info("Actions.getAllTasks.in");

        Long chatId = messageReceived.getChatId();
        SendMessage message = new SendMessage().setChatId(chatId);

        List<User> users = this.restClientsFactory.getUserServicesClient().getUserInfoByChatId(chatId.toString());
        if (Utilities.isEmpty(users) || users.size() != 1){
            LOGGER.error("Actions.getAllTasks - No se pudo encontrar el usuario");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_MONKEY + Constants.ERROR_MESSAGE));
            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.TRY_TO_LOGGIN + Constants.EMOJI_BULB));
            return Arrays.asList(message, message2);
        }

        List<Task> tasks = myTasks? this.restClientsFactory.getTaskServicesClient().getUserTasks(users.get(0).getId()):
                                    this.restClientsFactory.getTaskServicesClient().getAllTasks();
        LOGGER.info(myTasks.toString() + " " +Utilities.toJson(tasks)); //borrar                           
        if (Utilities.isEmpty(tasks)){
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_NUSE + Constants.NO_TASKS + Constants.EMOJI_NUSE));
            return Arrays.asList(message);
        }

        String text = "Tareas disponibles:";
        for (Task task : tasks){
            text = String.format("%s\n\t* %s - %s", text, task.getName(), task.getStatus());
        }
        message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_TASK + text));
        LOGGER.info("Actions.getAllTasks.out");
        return Arrays.asList(message);
    }

    
    public List<SendMessage> taskDone (List<String> params, Message messageReceived){
        LOGGER.info("Actions.taskDone.in");

        Long chatId = messageReceived.getChatId();
        SendMessage message = new SendMessage().setChatId(chatId);

        if (Utilities.isEmpty(params) || params.size() != 1){
            LOGGER.info("Actions.taskDone - Empty params");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_WARNING + Constants.WRONG_COMMAND + Constants.EMOJI_WARNING));

            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.TASK_DONE_USE));

            return Arrays.asList(message, message2);
        }

        List<User> users = this.restClientsFactory.getUserServicesClient().getUserInfoByChatId(chatId.toString());
        if (Utilities.isEmpty(users) || users.size() != 1){
            LOGGER.error("Actions.taskDone - No se pudo encontrar el usuario");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_MONKEY + Constants.ERROR_MESSAGE));
            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.TRY_TO_LOGGIN + Constants.EMOJI_BULB));
            return Arrays.asList(message, message2);
        }

        TaskDone taskDone = new TaskDone();
        taskDone.setStatus("FINALIZADO");
        taskDone.setName(params.get(0));

        this.restClientsFactory.getTaskServicesClient().updateTaskStatus(taskDone);
        message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_GOOD));

        LOGGER.info("Actions.taskDone.out");
        return Arrays.asList(message);
    }

    public List<SendMessage> assignTask (List<String> params, Message messageReceived){
        LOGGER.info("Actions.assignTask.in");

        Long chatId = messageReceived.getChatId();
        SendMessage message = new SendMessage().setChatId(chatId);

        if (Utilities.isEmpty(params) || params.size() != 1){
            LOGGER.info("Actions.assignTask - Empty params");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_WARNING + Constants.WRONG_COMMAND + Constants.EMOJI_WARNING));

            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.ASSIGN_TASK_USE));

            return Arrays.asList(message, message2);
        }

        List<User> users = this.restClientsFactory.getUserServicesClient().getUserInfoByChatId(chatId.toString());
        if (Utilities.isEmpty(users) || users.size() != 1){
            LOGGER.error("Actions.assignTask - No se pudo encontrar el usuario");
            message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_MONKEY + Constants.ERROR_MESSAGE));
            SendMessage message2 = new SendMessage().setChatId(chatId);
            message2.setText(EmojiParser.parseToUnicode(Constants.EMOJI_BULB + Constants.TRY_TO_LOGGIN + Constants.EMOJI_BULB));
            return Arrays.asList(message, message2);
        }

        TaskAssign taskAssign = new TaskAssign();
        taskAssign.setKey(users.get(0).getId());
        taskAssign.setName(params.get(0));

        taskAssign = 
            this.restClientsFactory.getTaskServicesClient().assignTask(taskAssign);

        message.setText(EmojiParser.parseToUnicode(Constants.EMOJI_GOOD + String.format(Constants.TASK_ASSIGNED, taskAssign.getName(), taskAssign.getKey())));

        LOGGER.info("Actions.assignTask.out");
        return Arrays.asList(message);
    }

    /**
     * Accion desconocida, no se ha reconocido el comando.
     * @param messageReceived mensaje recibido
     * @return
     */
    public List<SendMessage> action_unknown (Message messageReceived) {
        LOGGER.info("Actions.action_unknown.in");

        SendMessage message1 = new SendMessage().setChatId(messageReceived.getChatId());
        message1.setText(EmojiParser.parseToUnicode(Constants.EMOJI_PROHIBITED + Constants.UNKNOWN_ACTION + Constants.EMOJI_PROHIBITED));

        SendMessage message2 = new SendMessage().setChatId(messageReceived.getChatId());
        message2.setText(EmojiParser.parseToUnicode(Constants.REVIEW_COMMAND_LIST + Constants.EMOJI_BLINK));

        LOGGER.info("Actions.action_unknown.out");
        return Arrays.asList(message1, message2);
    }

       
}