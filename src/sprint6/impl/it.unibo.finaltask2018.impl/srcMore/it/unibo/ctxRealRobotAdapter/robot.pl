%====================================================================================
% Context ctxRealRobotAdapter  SYSTEM-configuration: file it.unibo.ctxRealRobotAdapter.robot.pl 
%====================================================================================
context(ctxrealrobotadapter, "localhost",  "TCP", "9010" ).  		 
%%% -------------------------------------------
qactor( adapter_to_physical_mbot , ctxrealrobotadapter, "it.unibo.adapter_to_physical_mbot.MsgHandle_Adapter_to_physical_mbot"   ). %%store msgs 
qactor( adapter_to_physical_mbot_ctrl , ctxrealrobotadapter, "it.unibo.adapter_to_physical_mbot.Adapter_to_physical_mbot"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evt_modelchanged,ctxrealrobotadapter,"it.unibo.ctxRealRobotAdapter.Evt_modelchanged","modelChanged").  
%%% -------------------------------------------

