public class PlayerStatus
{
    String name;
    Boolean connected;
    Boolean ready;
    int id;

    void setName(String name)
    {
        this.name=name;
    }

    void setConnected()
    {
        this.connected=true;
    }

    void setDisconnected()
    {
        this.connected=false;
    }

    void setReady()
    {
        this.ready=true;
    }

    void setId(int id)
    {
        this.id=id;
    }

    String getName()
    {
        return name;
    }

    Boolean getConnected()
    {
        return connected;
    }

    Boolean getReady()
    {
        return ready;
    }

    int getId()
    {
        return id;
    }

}
