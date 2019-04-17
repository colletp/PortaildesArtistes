<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Springboot Rest Client</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
	<link rel="icon" href="favicon.ico" />          
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
          <!-- chargement en dernier, sinon le style sera �cras� par celui du bootstrap  -->
    <link rel="stylesheet" href="mainCSS.css" />

</head>
<body>
	<div class="container">
		<div class="row" style="margin-top:20px">
	        <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">	        
				<div class="starter-template">
					<h3 class="titre1">Exemple de Client REST avec Spring Boot</h3>
					<h4 class="titre2">URL du serveur : ${urlServeur}</h4>
					<h4 class="titre2">Etat du serveur : ${pingServeur}</h4>
					<h4 class="titre2">Profile utilisateur : ${profileActif}</h4>
				</div>		
	        </div>
	    </div>
	</div>

  	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>	
</body>
</html>