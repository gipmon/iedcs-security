# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import uuid


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0001_initial'),
        ('books', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='File',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('identifier', models.CharField(default=uuid.uuid4, unique=True, max_length=128)),
                ('path', models.FileField(upload_to=b'books/%Y/%m/%d')),
                ('book', models.ForeignKey(to='books.Book')),
                ('buyer', models.ForeignKey(to='authentication.Account')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
