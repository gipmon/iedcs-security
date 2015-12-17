# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0007_tokenforcitizenauthentication'),
    ]

    operations = [
        migrations.AlterField(
            model_name='account',
            name='citizen_card_serial_number',
            field=models.CharField(max_length=256, unique=True, null=True, blank=True),
            preserve_default=True,
        ),
    ]
