%====================================================================================
% Context ctxRealLedAdapter  SYSTEM-configuration: file it.unibo.ctxRealLedAdapter.robot.pl 
%====================================================================================
context(ctxrealledadapter, "localhost",  "TCP", "9011" ).  		 
%%% -------------------------------------------
qactor( real_led , ctxrealledadapter, "it.unibo.real_led.MsgHandle_Real_led"   ). %%store msgs 
qactor( real_led_ctrl , ctxrealledadapter, "it.unibo.real_led.Real_led"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

