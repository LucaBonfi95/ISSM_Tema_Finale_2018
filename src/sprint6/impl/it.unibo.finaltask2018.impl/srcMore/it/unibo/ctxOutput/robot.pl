%====================================================================================
% Context ctxOutput  SYSTEM-configuration: file it.unibo.ctxOutput.robot.pl 
%====================================================================================
context(ctxresourcemodel, "localhost",  "TCP", "8099" ).  		 
context(ctxoutput, "localhost",  "TCP", "8098" ).  		 
%%% -------------------------------------------
qactor( mock_output_led , ctxoutput, "it.unibo.mock_output_led.MsgHandle_Mock_output_led"   ). %%store msgs 
qactor( mock_output_led_ctrl , ctxoutput, "it.unibo.mock_output_led.Mock_output_led"   ). %%control-driven 
qactor( mock_output_temperature , ctxoutput, "it.unibo.mock_output_temperature.MsgHandle_Mock_output_temperature"   ). %%store msgs 
qactor( mock_output_temperature_ctrl , ctxoutput, "it.unibo.mock_output_temperature.Mock_output_temperature"   ). %%control-driven 
qactor( mock_output_time , ctxoutput, "it.unibo.mock_output_time.MsgHandle_Mock_output_time"   ). %%store msgs 
qactor( mock_output_time_ctrl , ctxoutput, "it.unibo.mock_output_time.Mock_output_time"   ). %%control-driven 
qactor( adapter_to_pfrs_mbot , ctxoutput, "it.unibo.adapter_to_pfrs_mbot.MsgHandle_Adapter_to_pfrs_mbot"   ). %%store msgs 
qactor( adapter_to_pfrs_mbot_ctrl , ctxoutput, "it.unibo.adapter_to_pfrs_mbot.Adapter_to_pfrs_mbot"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(pfrs_event_driven,ctxoutput,"it.unibo.ctxOutput.Pfrs_event_driven","modelChanged").  
%%% -------------------------------------------

