%====================================================================================
% Context ctxResourceModel  SYSTEM-configuration: file it.unibo.ctxResourceModel.robot.pl 
%====================================================================================
context(ctxresourcemodel, "localhost",  "TCP", "8099" ).  		 
%%% -------------------------------------------
qactor( resource_model_led , ctxresourcemodel, "it.unibo.resource_model_led.MsgHandle_Resource_model_led"   ). %%store msgs 
qactor( resource_model_led_ctrl , ctxresourcemodel, "it.unibo.resource_model_led.Resource_model_led"   ). %%control-driven 
qactor( resource_model_time , ctxresourcemodel, "it.unibo.resource_model_time.MsgHandle_Resource_model_time"   ). %%store msgs 
qactor( resource_model_time_ctrl , ctxresourcemodel, "it.unibo.resource_model_time.Resource_model_time"   ). %%control-driven 
qactor( resource_model_temperature , ctxresourcemodel, "it.unibo.resource_model_temperature.MsgHandle_Resource_model_temperature"   ). %%store msgs 
qactor( resource_model_temperature_ctrl , ctxresourcemodel, "it.unibo.resource_model_temperature.Resource_model_temperature"   ). %%control-driven 
qactor( resource_model_robot , ctxresourcemodel, "it.unibo.resource_model_robot.MsgHandle_Resource_model_robot"   ). %%store msgs 
qactor( resource_model_robot_ctrl , ctxresourcemodel, "it.unibo.resource_model_robot.Resource_model_robot"   ). %%control-driven 
qactor( resource_model_sonar , ctxresourcemodel, "it.unibo.resource_model_sonar.MsgHandle_Resource_model_sonar"   ). %%store msgs 
qactor( resource_model_sonar_ctrl , ctxresourcemodel, "it.unibo.resource_model_sonar.Resource_model_sonar"   ). %%control-driven 
%%% -------------------------------------------
eventhandler(evt_obstacle,ctxresourcemodel,"it.unibo.ctxResourceModel.Evt_obstacle","obstacleDetected").  
eventhandler(evt_robot,ctxresourcemodel,"it.unibo.ctxResourceModel.Evt_robot","robotMovement").  
eventhandler(logger,ctxresourcemodel,"it.unibo.ctxResourceModel.Logger","modelChanged").  
%%% -------------------------------------------

