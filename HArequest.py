# -*- coding: utf-8 -*-
import urllib2
import csv
import sys
import json
import base64
import json
import optparse
import datetime

from urllib2 import Request,urlopen
from restkit import Resource, Connection, BasicAuth, request
from json import dumps, load
from base64 import b64encode
from pprint import pprint
from requests.auth import HTTPBasicAuth
keyId='W7HUqrNARndEWCXxyj5zwLZVBd174FPnCfzuqSxF'
baseURL = 'https://api.data.gov/nrel/alt-fuel-stations/v1/'
stationbylocationURL= baseURL+'nearest.json?api_key='+keyId
headers = {"Authorization": " Basic " , "Content-Type": "application/json"}



def getstationbyLocation(locationCity,locationState):
    getlocationURL=stationbylocationURL+"&location="+locationCity+"+"+locationState
    print getlocationURL
    objResponse=fetchRequestResponse_withoutdata(getlocationURL,headers)
    #testkey=objResponse['latitude']
    for x,y in enumerate(objResponse['fuel_stations']):
        try:
             stationId = str(y['id'])
             stationName= str(y['station_name'])
             if stationName in 'HYATT AUSTIN': 
             	print str(y['street_address'])
                print str(y['id'])
                teststationId=stationId
        except:
          continue
    return teststationId


def getstationbyId(stationId):
    getstationidURL=baseURL+stationId+".json?api_key="+keyId
    print getstationidURL
    objResponse=fetchRequestResponse_withoutdata(getstationidURL,headers)
    streetAddress=objResponse['alt_fuel_station']['street_address']
    streetZip =objResponse['alt_fuel_station']['zip']
    stationState=objResponse['alt_fuel_station']['state']
    print streetAddress
    print streetZip
    print stationState
    if streetAddress in '208 Barton Springs Rd' and streetZip in '78704' and  stationState in 'TX':
     print 'Test Passed'
    else:
      print 'Test Failed'
     
def fetchRequestResponse_withoutdata(requesturl,headers):
     opener = urllib2.build_opener(urllib2.HTTPHandler)
     request = urllib2.Request(requesturl,headers=headers)
     js_res = opener.open(request)
     objResponse = load(js_res)
     return objResponse




stationId=getstationbyLocation("Austin","Tx")
getstationbyId(stationId)
#print latitudekey
