# -------------------------------------------------------------
# led25GpioTurnOn.sh
# Key-point: we can manage a device connected on a GPIO pin by
# using the GPIO shell library. 
# The pin 25 is physical 22 and Wpi 6.
#	sudo bash led25GpioTurnOn.sh
# -------------------------------------------------------------
gpio mode 28 out #
gpio write 28 1 #
