package timer.com.maydaysdk;

/**
 * Created by sunilkumar.h on 5/27/2016.
 */
public class MayDayIconConfiguration {

    private static MayDayIconConfiguration instance;
    int callAnswerIcon;
    int callHangIcon;
    int maximiseIcon;
    int minimiseIcon;
    int micOnIcon;
    int micOffIcon;
    int chatMinimiseIcon;
    int chatMaximiseIcon;
    int chatCloseIcon;
    int chatSendIcon;

    private MayDayIconConfiguration() {

    }

    public static MayDayIconConfiguration getInstance() {
        if (instance == null) {
            instance = new MayDayIconConfiguration();
        }
        return instance;
    }

    public int getCallAnswerIcon() {
        return callAnswerIcon;
    }

    public void setCallAnswerIcon(int callAnswerIcon) {
        this.callAnswerIcon = callAnswerIcon;
    }

    public int getCallHangIcon() {
        return callHangIcon;
    }

    public void setCallHangIcon(int callHangIcon) {
        this.callHangIcon = callHangIcon;
    }

    public int getMaximiseIcon() {
        return maximiseIcon;
    }

    public void setMaximiseIcon(int maximiseIcon) {
        this.maximiseIcon = maximiseIcon;
    }

    public int getMinimiseIcon() {
        return minimiseIcon;
    }

    public void setMinimiseIcon(int minimiseIcon) {
        this.minimiseIcon = minimiseIcon;
    }

    public int getMicOnIcon() {
        return micOnIcon;
    }

    public void setMicOnIcon(int micOnIcon) {
        this.micOnIcon = micOnIcon;
    }

    public int getMicOffIcon() {
        return micOffIcon;
    }

    public void setMicOffIcon(int micOffIcon) {
        this.micOffIcon = micOffIcon;
    }

    public int getChatMinimiseIcon() {
        return chatMinimiseIcon;
    }

    public void setChatMinimiseIcon(int chatMinimiseIcon) {
        this.chatMinimiseIcon = chatMinimiseIcon;
    }

    public int getChatMaximiseIcon() {
        return chatMaximiseIcon;
    }

    public void setChatMaximiseIcon(int chatMaximiseIcon) {
        this.chatMaximiseIcon = chatMaximiseIcon;
    }

    public int getChatCloseIcon() {
        return chatCloseIcon;
    }

    public void setChatCloseIcon(int chatCloseIcon) {
        this.chatCloseIcon = chatCloseIcon;
    }

    public int getChatSendIcon() {
        return chatSendIcon;
    }

    public void setChatSendIcon(int chatSendIcon) {
        this.chatSendIcon = chatSendIcon;
    }


}
