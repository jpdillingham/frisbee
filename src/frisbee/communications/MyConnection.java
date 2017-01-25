package frisbee.communications;

import frisbee.exceptions.LoggedException;
import frisbee.messaging.Message;

public class MyConnection extends Connection {

    @Override
    public void open() throws LoggedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void close() throws LoggedException {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() throws LoggedException {
        // TODO Auto-generated method stub
    }

    @Override
    public void reset() throws LoggedException {
        // TODO Auto-generated method stub

    }

    @Override
    protected Message readMessage() throws LoggedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void writeMessage(Message message) throws LoggedException {
        // TODO Auto-generated method stub

    }

    /**
     * This method polls or listens on a connection for new incoming data
     *
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        //TODO Auto-generated method stub
        this.setChanged();
        this.notifyObservers(null);
    }

}
