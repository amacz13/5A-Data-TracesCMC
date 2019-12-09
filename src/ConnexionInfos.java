public class ConnexionInfos {

    public String user;
    private int readActivity = 0;
    private int writeActivity = 0;
    private int totalActivity = 0;

    public ConnexionInfos(String username){
        this.user = username;
    }

    public int getReadActivity() {
        return readActivity;
    }

    public void setReadActivity(int readActivity) {
        this.readActivity = readActivity;
        this.totalActivity = this.readActivity+this.writeActivity;
    }

    public int getWriteActivity() {
        return writeActivity;
    }

    public void setWriteActivity(int writeActivity) {
        this.writeActivity = writeActivity;
        this.totalActivity = this.readActivity+this.writeActivity;
    }

    public int getTotalActivity() {
        return totalActivity;
    }
}
