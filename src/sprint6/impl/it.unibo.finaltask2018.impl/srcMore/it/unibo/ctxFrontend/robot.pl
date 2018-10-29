%====================================================================================
% Context ctxFrontend  SYSTEM-configuration: file it.unibo.ctxFrontend.robot.pl 
%====================================================================================
context(ctxresourcemodel, "localhost",  "TCP", "8099" ).  		 
context(ctxfrontend, "localhost",  "TCP", "8020" ).  		 
%%% -------------------------------------------
qactor( qa2frontend , ctxfrontend, "it.unibo.qa2frontend.MsgHandle_Qa2frontend"   ). %%store msgs 
qactor( qa2frontend_ctrl , ctxfrontend, "it.unibo.qa2frontend.Qa2frontend"   ). %%control-driven 
qactor( frontend2qa , ctxfrontend, "it.unibo.frontend2qa.MsgHandle_Frontend2qa"   ). %%store msgs 
qactor( frontend2qa_ctrl , ctxfrontend, "it.unibo.frontend2qa.Frontend2qa"   ). %%control-driven 
%%% -------------------------------------------
%%% -------------------------------------------

