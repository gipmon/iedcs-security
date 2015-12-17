# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import uuid


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0006_auto_20151216_1504'),
    ]

    operations = [
        migrations.CreateModel(
            name='TokenForCitizenAuthentication',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('identifier', models.CharField(default=uuid.uuid4, unique=True, max_length=100)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
    ]
