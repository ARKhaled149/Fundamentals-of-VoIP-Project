package test;
/**

* You should use the SIPNotification class instead of this!
* This class will create an UDP listener to receive the notifications from JVoIP
*/



import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import webphone.webphone;

public class SIPNotificationsUDP extends Thread
{
   boolean terminated = false;
   byte[] buf = null;
   DatagramSocket socket = null;
   DatagramPacket packet = null;

   /**
   * ctor
   */
   public SIPNotificationsUDP(webphone webphoneobj_in)
   {
	   //the webphone object is not needed here
   }

   /**
   *Start listening on UDP 19421
   */
   public boolean Start()
   {

       try{
           //socket = new DatagramSocket(19421);
           InetSocketAddress localaddress = new InetSocketAddress("127.0.0.1", 19421);
           try{
               //try to bind to 127.0.0.1:19421
               socket = new DatagramSocket(localaddress);
           }catch(Exception e)
           {
               //if failed to bind, then try again with any port
               socket = new DatagramSocket();
           }
           buf = new byte[3600];
           packet = new DatagramPacket(buf, buf.length);
           terminated = false;
           this.start();
           System.out.println("sip notifications started");
           return true;
       }catch(Exception e) {System.out.println("Exception at SIPNotificationsUDP Start: "+e.getMessage()+"\r\n"+e.getStackTrace()); }
       return false;
   }

   /**
      *Send to JVoIP. This is used only for ping
   */
   public boolean Send(String ip, int port, String msg)
   {
       try{
           byte[] buf = msg.getBytes();
           //socket.send(new DatagramPacket(buf, buf.length,InetAddress.getByName(ip), port));
           socket.send(new DatagramPacket(buf, buf.length,new InetSocketAddress(ip, port)));
           return true;
       }catch(Exception e) {  }   //a socket close exception might be raised here if jvoip is not listening yet
       return false;
   }

   /**
      *Send a ping packet to JVoIP. This can help in discovery in some circumstances
   */
   public void Ping()
   {
       //this is just to make sure that JVoIP will find us even if we are not running at port 19421
       Send("127.0.0.1", 19422, "BOFCOMMANDBOFLINEfunction=API_TestEOFLINEEOFCOMMAND");
   }

   /**
   * signal terminate and close the socket
   */
   public void Stop()
   {
       try{
           terminated = true;
           if (socket != null)
               socket.close();
           //a socket close exception might be raised here which is safe to catch and hide
       }catch(Exception e) { }
   }

   //

   /**
   * blocking read in this thread
   */
   public void run()
   {
        try{
            while (!terminated) {
                packet.setData(buf, 0, buf.length);
                packet.setLength(buf.length);
                socket.receive(packet);
                if (packet != null && packet.getLength() > 0) {
                    String str = new String(packet.getData(), 0,
                                            packet.getLength());
                    ProcessNotifications(str);
                }
            }
        }catch(Exception e)
        {
           if(!terminated) System.out.println("Exception at SIPNotificationsUDP run: "+e.getMessage()+"\r\n"+e.getStackTrace());
        }

    }


    /**
    * all messages from JVoIP will be routed to this function.
    * parse and process them after your needs regarding to your application logic
    */

    public void ProcessNotifications(String msg)
    {
        try{
            System.out.println("\tREC FROM JVOIP: " + msg);
            //frame.jTextArea1.append(msg);
                        //TODO: process notifications here (change your user interface or business logic depending on the sipstack state / call state by parsing the strings receiver here).
                        //See the "Notifications" chapter in the documentation for the expecteddetails.
        }catch(Exception e) { System.out.println("Exception at SIPNotificationsUDP ProcessNotifications: "+e.getMessage()+"\r\n"+e.getStackTrace()); }
    }
}

