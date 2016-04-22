function calculateBMI(height, weight){
	var bmi = (weight.value/(height.value*height.value))*10000;
	bmi = Math.round(bmi*100)/100;
	if (isNaN(bmi)){
		resetBmi(height, weight)
	}
	else{
		h2Text = document.createTextNode("Your BMI is " + bmi);
		target = document.getElementById("h2ID1");
		target.removeChild(target.firstChild);
		target.appendChild(h2Text);
						
		if (bmi < 18.5){
			h2Text = document.createTextNode("Category: Underweight");
		}
		else if (bmi >= 18.5 && bmi < 25){
			h2Text = document.createTextNode("Category: Normal");
		}
		else if (bmi >= 25 && bmi < 30){
			h2Text = document.createTextNode("Category: Overweight");
		}
		else{
			h2Text = document.createTextNode("Category: Obese");
		}
			target = document.getElementById("h2ID2");
			target.removeChild(target.firstChild);
			target.appendChild(h2Text);
	}
}
				
function resetBmi(height, weight){
	height.value = "";
	weight.value = "";
	h2Text = document.createTextNode("Your BMI is ");
	target = document.getElementById("h2ID1");
	target.removeChild(target.firstChild);
	target.appendChild(h2Text);
					
	h2Text = document.createTextNode("Category:");
	target = document.getElementById("h2ID2");
	target.removeChild(target.firstChild);
	target.appendChild(h2Text);
}