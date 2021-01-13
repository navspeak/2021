
# OAuth 2.0 

## Table of Content
* [Analogy](#Hotel-and-App-Analogy)
* [OAuth 2.0 Code Flow](OAuth-2.0-Code-Flow)



## Hotel and App  Analogy:


| Hotel | App  | Oauth | description |
| --- |--------| -----|-------------|
|Manager|Me, you, end user|Resource Owner|wants client app to do things on its behalf|
|Repair Guy|Yelp|Client App |needs authz to interact with an API on user's behalf|
|Receptionist|Google|Authz Server|grants access in form of tokens to a client app/
|Rooms|Google Contact API|Resource Server|has an API that an app can use if presented with a valid token|

## OAuth 2.0 Code Flow

![OAuth 2.0 Code Flow](codeFlow.PNG)

* Front Channel 

#### 1. Starting the flow (Authorization Step)
<pre>
Request Sent from Yelp to Google                      Google to Yelp on No Consent        On Consent from user
===========================================================================================================
GET https://accounts.google.com/o/oauth2/v2/auth?     https://yelp.com/callback?         https://yelp.com/callback?           
	client_id=abc123&                                   error=access_denied&                code=oTynkhkl7hk&
	redirect_uri=https://yelp.com/callback&             error_description=The user did      state=foobar
	score=profile contacts &                                not consent
	response_type=code&
	state=foobar
</pre>

#### 2. Exchange code for an access token:
<pre>

POST www.googleapis/com/outh2/v4/token
Content-Type: application/x-www-form-urlencoded

code=oTynkhkl7hk&
client_id=abc123&
client_secret=secret123&
grant_type=authorization_code

Authorization Server returns an access token:
{
	access_token: "fEgkkkjhgkkhj7jkjll",
	expires_in: 3920,
	token_type" "Bearer"
}
</pre>
#### 3. Use the access token
<pre>
GET api.google.com/some/endpoint
Authorization: Bearer "fEgkkkjhgkkhj7jkjll"
</pre>