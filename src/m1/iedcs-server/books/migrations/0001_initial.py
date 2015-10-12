# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.core.validators
import uuid


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Book',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('identifier', models.CharField(default=uuid.uuid4, unique=True, max_length=128)),
                ('name', models.CharField(max_length=128, validators=[django.core.validators.MinLengthValidator(1)])),
                ('production_date', models.DateField()),
                ('author', models.CharField(max_length=128, validators=[django.core.validators.MinLengthValidator(1)])),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
