log.info "Groovy script has been called"

switch(httpRequest.getContextPath()){
	case "/opel/vectra" : responseContent = "opel vectra info"
	break;

	case "/sony/tv/md350" : responseContent = "Sony televesion model md350 info"
	break;

	case "/australia/queensland/brisbane" : responseContent = "Info on Brisbane"
	break;
}