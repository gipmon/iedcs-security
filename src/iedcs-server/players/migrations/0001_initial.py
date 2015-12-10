# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Device',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('unique_identifier', models.CharField(default=b'', unique=True, max_length=254)),
                ('cpu_model', models.CharField(default=b'', max_length=128)),
                ('op_system', models.CharField(default=b'', max_length=128)),
                ('ip', models.CharField(default=b'', max_length=128)),
                ('country', models.CharField(default=b'', max_length=128)),
                ('timezone', models.CharField(default=b'', max_length=128)),
                ('host_name', models.CharField(default=b'', max_length=128)),
                ('public_key', models.FileField(upload_to=b'')),
                ('created_at', models.DateTimeField(auto_now_add=True)),
                ('updated_at', models.DateTimeField(auto_now=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='DeviceOwner',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('device', models.ForeignKey(to='players.Device')),
                ('owner', models.ForeignKey(to=settings.AUTH_USER_MODEL)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Player',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('version', models.CharField(default=b'', unique=True, max_length=128)),
                ('public_key', models.FileField(upload_to=b'')),
                ('private_key', models.FileField(upload_to=b'')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='PlayerVersion',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('version', models.CharField(max_length=128)),
                ('public_key', models.FileField(upload_to=b'')),
                ('private_key', models.FileField(upload_to=b'')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.AlterUniqueTogether(
            name='deviceowner',
            unique_together=set([('owner', 'device')]),
        ),
    ]
