%====================================================================================
% Context ctxApplicationLogic  SYSTEM-configuration: file it.unibo.ctxApplicationLogic.robot.pl 
%====================================================================================
context(ctxresourcemodel, "localhost",  "TCP", "8099" ).  		 
context(ctxapplicationlogic, "localhost",  "TCP", "8097" ).  		 
%%% -------------------------------------------
qactor( robot_movement_finder , ctxapplicationlogic, "it.unibo.robot_movement_finder.MsgHandle_Robot_movement_finder"   ). %%store msgs 
qactor( robot_movement_finder_ctrl , ctxapplicationlogic, "it.unibo.robot_movement_finder.Robot_movement_finder"   ). %%control-driven 
qactor( blink_controller , ctxapplicationlogic, "it.unibo.blink_controller.MsgHandle_Blink_controller"   ). %%store msgs 
qactor( blink_controller_ctrl , ctxapplicationlogic, "it.unibo.blink_controller.Blink_controller"   ). %%control-driven 
qactor( initial_conditions_checker , ctxapplicationlogic, "it.unibo.initial_conditions_checker.MsgHandle_Initial_conditions_checker"   ). %%store msgs 
qactor( initial_conditions_checker_ctrl , ctxapplicationlogic, "it.unibo.initial_conditions_checker.Initial_conditions_checker"   ). %%control-driven 
qactor( stop_conditions_checker , ctxapplicationlogic, "it.unibo.stop_conditions_checker.MsgHandle_Stop_conditions_checker"   ). %%store msgs 
qactor( stop_conditions_checker_ctrl , ctxapplicationlogic, "it.unibo.stop_conditions_checker.Stop_conditions_checker"   ). %%control-driven 
qactor( sonar_checker , ctxapplicationlogic, "it.unibo.sonar_checker.MsgHandle_Sonar_checker"   ). %%store msgs 
qactor( sonar_checker_ctrl , ctxapplicationlogic, "it.unibo.sonar_checker.Sonar_checker"   ). %%control-driven 
qactor( robot_basic_movements , ctxapplicationlogic, "it.unibo.robot_basic_movements.MsgHandle_Robot_basic_movements"   ). %%store msgs 
qactor( robot_basic_movements_ctrl , ctxapplicationlogic, "it.unibo.robot_basic_movements.Robot_basic_movements"   ). %%control-driven 
qactor( collision_detector , ctxapplicationlogic, "it.unibo.collision_detector.MsgHandle_Collision_detector"   ). %%store msgs 
qactor( collision_detector_ctrl , ctxapplicationlogic, "it.unibo.collision_detector.Collision_detector"   ). %%control-driven 
qactor( handle_planner , ctxapplicationlogic, "it.unibo.handle_planner.MsgHandle_Handle_planner"   ). %%store msgs 
qactor( handle_planner_ctrl , ctxapplicationlogic, "it.unibo.handle_planner.Handle_planner"   ). %%control-driven 
qactor( mock_sender , ctxapplicationlogic, "it.unibo.mock_sender.MsgHandle_Mock_sender"   ). %%store msgs 
qactor( mock_sender_ctrl , ctxapplicationlogic, "it.unibo.mock_sender.Mock_sender"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

