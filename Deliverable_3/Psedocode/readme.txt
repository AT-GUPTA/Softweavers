Pseudocode

Exponential Function:

Main Program
Construct Logger in exponents.txt
	While loop:
		Prompt user to enter power expression a(b^x)
		If expression is blank:
			Break
		End if
		Parse power expression into a, b, x
		Call a * Exponential.pow(b, x)
		Log result to Logger
	End while

Exponential pow() Function
	Function pow(b, x)
		If b is negative:
			If x is integer:
				If x is even:
					Return pow(-b, x)
				Else x is odd:
					Return -1 * pow(-b, x)
				End if
			Else x is not integer:
				Log error: unreal solution
			End if
		Else if x is negative:
			Return 1 / pow(b, -x)
		End if
		If exponent is greater than 1:
			Return pow(b, x/2) * pow(b, x/2)
		Else:
			Apply Newton’s method to solve fractional exponent
	End function
