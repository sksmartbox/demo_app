//const baseURL = "http://sampra.flexsin.org/backend/public/api/";
const baseURL = "http://100.100.7.34:8000/api/";
var count = 0;
//To request for post api
export function postAPI(method, data) {
  console.log("url--" + baseURL + method);
  console.log("Request--> ", JSON.stringify(data))
  return fetch(baseURL + method, {
    method: "POST",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    timeout: 5000,
    body: JSON.stringify(data)//formBody
  })
    .then(response => {
      console.log('Response---> ', response)
      var param = JSON.parse(response._bodyText);
      return param;
    })
    .catch(function (error) {
      console.log("Error--->" + error);
      return error;
    });
}

//To request for post api using form data
export function postFormDataAPI(method, data) {
  console.log("url--" + baseURL + method);
  let options = {
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    method: 'POST'
  };

  options.body = new FormData();
  for (let key in data) {
    options.body.append(key, data[key]);
  }
  console.log("Request--> ", options.body)

  return fetch(baseURL + method, options)
    .then(response => {
      console.log('Response---> ', response)
      var param = response.json();
      return param;
    })
    .catch(function (error) {
      console.log("Error--->" + error);
      return error;
    });
}

//To request for get api
export async function getAPI(method) {
  try {
    let response = await fetch(baseURL + method);
    console.log("responseeeee", response)
    let responseJson = await response.json();
    return responseJson;
  } catch (error) {
    return error;
  }
}

