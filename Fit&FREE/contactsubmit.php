<!DOCTYPE html>
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/> <!-- sets viewport to device width -->
	<title>Fit and Free</title>

	<link rel ="stylesheet" href="css/reset.css" type="text/css"/> 
	<link rel ="stylesheet" href="css/style.css" type="text/css"/> 
	<link rel ="stylesheet" href="css/thankyou.css" type="text/css"/>
	<link href="lightbox/css/lightbox.css" rel="stylesheet" /> <!--Lightbox plugin for images [CSS] -->
	<link href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700|Open+Sans:600italic,300italic,400,300|Poiret+One' rel='stylesheet' type='text/css'> <!--Google fonts OpenSans -->

	<script type="text/javascript" src="js/modernizr.js"></script> <!--Feature detection -->
	<script type="text/javascript" src="js/respond.min.js"></script> <!--Responsive design support browsers without CSS Media Queries support -->
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.js"></script> <!--JQUERY libraries -->
	<script type="text/javascript" src="lightbox/js/lightbox.js"></script> <!--Lightbox plugin [JS]-->
	<script type="text/javascript" src="js/prefixfree.min.js"></script> <!--Script to eliminate need for browser specific prefixes-->
	<script type="text/javascript" src="js/unslider.js"></script> <!--Slider javascript for header-->
	<script type="text/javascript">
		$(function() {
    $('.banner').unslider();
});
</script>


</head>

<body>



	<header>
		<h1><a href="index.html">fit&amp;<span>FREE</span></a></h1>
		<p>Free your life.</p>

		
		<nav>
			<ul>
				<li><a href="index.html">Home</a></li>
				<li><a href="tools.html">Tools</a>
					<ul>
						<li><a href="tools.html#tools_info">Info</a>
							<ul>
								<li><a href="bmicalc.html">BMI Calculator</a></li>
								<li><a href="bmrcalc.html">Caloric needs</a></li>

							</ul>
						</li>
						<li><a href="tools.html#tools_diet">Diet</a>
							<ul>
								<li><a href="foodjournaldeveloping.html">Food Journal</a></li>
								<li><a href="recipebookdeveloping.html">Recipe Book</a></li>
								<li><a href="caloriecounterdeveloping.html">Calorie Counter</a></li>
							</ul>
						<li><a href="tools.html#tools_exercise">Exercise</a>
							<ul>
								<li><a href="exerciseplannerdeveloping.html">Exercise Planner</a></li>
								<li><a href="caloricexpendituredeveloping.html">Caloric Expenditure</a></li>
							</ul>
						</li>
						</li>
					</ul>
				</li>
				<li><a href="news.html">News/Media</a>
					<ul>
						<li><a href="news.html">Articles</a>
						</li>
						<li><a href="media.html">Media</a>
						</li>
					</ul>
				</li>
				<li><a href="info.html"><strong>Info</strong></a>
					<ul>
						<li><a href="info.html">About Us</a>
							<ul>
								<li><a href="info.html#info_vision">Our Vision</a></li>
								<li><a href="info.html#info_privacy">Privacy</a></li>
							</ul>
						</li>
						<li><a href="contact.html">Contact</a></li>
						<li><a href="help.html">Help/FAQ</a></li>
					</ul>
				</li>
				<li><a href="signuplogin.html"><span>Sign up/Log In</span></a></li>
				
			</ul>
		</nav>
	</header>

	<section id="space">
		
	</section>
		

	<div class="pagetitle">
		<h1>Form Submitted</h1>
	</div>

	<section id="infoarticles">
		<article>
			
		
			<?php


$name = $_POST['name'];
$email = $_POST['email'];
$message = $_POST['message'];
$selection = $_POST['selection'];

if(empty($name)||empty($email))
{
	echo "Name and e-mail are mandatory";
	exit;
}

$email_subject = 'New Form Submission';
$email_body = 'You have received a new message from the user $name, with e-mail $email. \n It is a $selection type of message with the following content.\n $message';

echo "<p>Thank you for your submission! We will contact you shortly regarding your $selection.\n Please review if the following message is correct.</p>";
echo "<p>";
echo '<em>Your name</em>: '. $name ."<br>";
echo '<em>Your e-mail</em>: '. $email ."<br>";
echo '<em>Your message:</em><br><p> '. $message ."</p><br>";
echo '<em>Your type of message:</em> '. $selection ."<br>";
echo "</p>";
mail("fitfree9@gmail.com",$email_subject,$email_body);

?>

			

		</article>

	</section>
		<br class="clear"/>

	<section id="tools">
		
		<a id="tools_info"><h2>Suggested Tools</h2></a>
		<article>

			<a href="bmicalc.html"><img src="img/ICONS/Calculator.png"/></a>
			<a href="bmicalc.html"><h3>BMI Calculator</h3><a href="bmicalc.html">
			<p>
				Quickly use our simple BMI Calculator to see where you are on your path away from obesity!
			</p>
		</article>

		

		<article>
			<a href="bmrcalc.html"><img src="img/ICONS/caloricneeds.png"/></a>
			<a href="bmrcalc.html"><h3>Caloric Needs Calculator</h3></a>
			<p>
				How many calories can you afford to eat per day? Find out with our BMR (Basal Metabolic Rate) calculator.
			</p>
		</article>

		<article>
			<a href="exerciseplannerdeveloping.html"><img src="img/ICONS/Calendar.png"/></a>
			<a href="exerciseplannerdeveloping.html"><h3>Exercise Planner</h3></a>
			<p>
				Keeping a good exercise schedule will not only help you relieve your stress, but help keep those kilos off!
			</p>
		</article>

		

	

		<br class="clear"/>
		

	</section>


	





<footer>
	<h2 class="hidden">Our footer</h2>
	<section id="copyright">

		<div class="wrapper">
			<div class="social">
				<a href="https://www.facebook.com/pages/FitFree/1541721132729648"><img src="img/ICONS/facebook.png" alt="facebook" width="30"/></a>
				<a href="mailto:fitfree9@gmail.com"><img src="img/ICONS/Gmail.png" alt="gmail" width="30"/></a>
				<a href="javascript:void(0)"><img src="img/ICONS/YouTube.png" alt="youtube" width="30"/></a>
				<a href="https://twitter.com/fitfree1"><img src="img/ICONS/twitter.png" alt="twitter" width="30"/></a>
			</div>
				&copy; Copyright 2014 by <a href="">Fit&amp;Free</a>. All Rights Reserved. <div id="footlinks"><span><a href="sitemap.html">SITEMAP</a></span>|<span><a href="contact.html">Contact us</a></span>|<span><a href="info.html#info_privacy">Privacy</a></span></div>
		</div>
	</section>
	</footer>


</body>
</html>