%====================================================================================
% Context ctxInput  SYSTEM-configuration: file it.unibo.ctxInput.robot.pl 
%====================================================================================
context(ctxresourcemodel, "localhost",  "TCP", "8099" ).  		 
context(ctxinput, "localhost",  "TCP", "8096" ).  		 
%%% -------------------------------------------
qactor( temperature_sensor_adapter , ctxinput, "it.unibo.temperature_sensor_adapter.MsgHandle_Temperature_sensor_adapter"   ). %%store msgs 
qactor( temperature_sensor_adapter_ctrl , ctxinput, "it.unibo.temperature_sensor_adapter.Temperature_sensor_adapter"   ). %%control-driven 
qactor( timer_adapter , ctxinput, "it.unibo.timer_adapter.MsgHandle_Timer_adapter"   ). %%store msgs 
qactor( timer_adapter_ctrl , ctxinput, "it.unibo.timer_adapter.Timer_adapter"   ). %%control-driven 
qactor( input_element , ctxinput, "it.unibo.input_element.MsgHandle_Input_element"   ). %%store msgs 
qactor( input_element_ctrl , ctxinput, "it.unibo.input_element.Input_element"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

