package test;



import webphone.*; 


public class VoIPProject{

    static webphone webphoneobj = null;
    static SIPNotifications sipnotifications = null;
    
    
    public VoIPProject() {
        start_Jvoip();
    }

    
    public static void main(String[] args) {
    	MyFrame frame = new MyFrame();
        try {
        	new VoIPProject(); // or joe123
            
        }catch(Exception e) {System.out.println("Exception at main: "+e.getMessage()+"\r\n"+e.getStackTrace()); }
        
    }

    
    void start_Jvoip()
    {
        try{
            System.out.println("starting...");

            //create a JVoIP instance
            webphoneobj = new webphone();

            //create the SIPNotifications object to catch the events from JVoIP
            sipnotifications = new SIPNotifications(webphoneobj);
            //start receiving the SIP notifications

            sipnotifications.Start();
            //note: not recommended but it is also possible to receive the notifications via UDP packets instead of API_GetNotifications polling. For that just use the depreacted SIPNotificationsUDP class instead of SIPNotifications class

            //Thread.sleep(100); //you might wait a bit for the JVoIP to construct itself

            //set parameters
            webphoneobj.API_SetParameter("loglevel", "1"); //for development you should set the loglevel to 5. for production you should set the loglevel to 1
            webphoneobj.API_SetParameter("logtoconsole", "true"); //if the loglevel is set to 5 then a log window will appear automatically. it can be disabled with this setting
            webphoneobj.API_SetParameter("polling", "3"); //we will use the API_GetNotifications from our notifications thread, so we can safely disable socket/webphonetojs with this setting
            webphoneobj.API_SetParameter("startsipstack", "1"); //auto start the sipstack
            webphoneobj.API_SetParameter("serveraddress", "voip.mizu-voip.com"); // specify your voip server address
            webphoneobj.API_SetParameter("register", "1"); //auto register (set to 0 if you don't need to register or if you wish to call the API_Register explicitely later or set to 2 if must register)
            //webphoneobj.API_SetParameter("proxyaddress", "xxx");  //set this if you have a (outbound) proxy
            webphoneobj.API_SetParameter("transport", "2");  //the default transport is UDP. Set to 1 if you need TCP or to 2 if you need TLS
            //webphoneobj.API_SetParameter("realm", "xxx");  //your sip realm. it have to be set only if it is different from the serveraddress
            //webphoneobj.API_SetParameter("127.0.0.1", "voip.mizu-voip.com"); //your sip server domain or IP:port (the port number must be set only if not the standard 5060)
                       
            //register to sip server (optional)
//            System.out.println("registering...");
//            webphoneobj.API_Register();
            

          
                      

        }catch(Exception e) {System.out.println("Exception at start_Jvoip: "+e.getMessage()+"\r\n"+e.getStackTrace()); }

    }

    
}

