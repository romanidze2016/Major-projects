function calculateBMR(height, weight, age, gender){
	var bmr;
	var xyu;
	if (gender.options[gender.selectedIndex].value == "male"){
		bmr = 66 + 13.8*weight.value + 5*height.value - 6.8*age.value;
	} else{
		bmr = 655 + 9.6*weight.value + 1.8*height.value - 4.7*age.value;
	}
	bmr = Math.round(bmr*100)/100;

	if (isNaN(bmr)){
		resetBmi(height, weight)
	}
	else{
		h2Text = document.createTextNode("Your BMR is " + bmr + " calories/day");
		target = document.getElementById("h2ID1");
		target.removeChild(target.firstChild);
		target.appendChild(h2Text);
	}
}
				
function resetBMR(height, weight, age){
	height.value = "";
	weight.value = "";
	age.value = "";
	h2Text = document.createTextNode("Your BMR is ");
	target = document.getElementById("h2ID1");
	target.removeChild(target.firstChild);
	target.appendChild(h2Text);
}