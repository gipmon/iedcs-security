# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('players', '0001_initial'),
    ]

    operations = [
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
        migrations.AlterUniqueTogether(
            name='deviceowner',
            unique_together=set([('owner', 'device')]),
        ),
        migrations.AlterUniqueTogether(
            name='device',
            unique_together=set([]),
        ),
        migrations.RemoveField(
            model_name='device',
            name='owner',
        ),
    ]
