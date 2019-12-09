public class ConnexionInfos {

    public String user;
    public String date;
    private int readActivity = 0;
    private int writeActivity = 0;
    private int totalActivity = 0;

    public ConnexionInfos(String username, String date){
        this.user = username;
        this.date = date;
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
