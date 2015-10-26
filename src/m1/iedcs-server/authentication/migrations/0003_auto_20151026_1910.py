# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import authentication.models


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0002_account_username'),
    ]

    operations = [
        migrations.CreateModel(
            name='UserCollectedData',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('cpu_model', models.CharField(default=b'', max_length=128)),
                ('op_system', models.CharField(default=b'', max_length=128)),
                ('ip', models.CharField(default=b'', max_length=128)),
                ('country', models.CharField(default=b'', max_length=128)),
                ('timezone', models.CharField(default=b'', max_length=128)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.AddField(
            model_name='account',
            name='user_data',
            field=models.ForeignKey(default=authentication.models.UserCollectedData, to='authentication.UserCollectedData'),
            preserve_default=True,
        ),
    ]
