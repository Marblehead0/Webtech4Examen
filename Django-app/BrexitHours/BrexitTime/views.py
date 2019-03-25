from django.shortcuts import render
from django.http import HttpResponse
from BrexitTime.models import BlogArticle

# Create your views here.

from django.contrib.auth import authenticate,login

def index(request):
    return render(request,"index.html")