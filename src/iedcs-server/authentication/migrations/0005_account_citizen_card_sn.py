# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0004_account_has_cc'),
    ]

    operations = [
        migrations.AddField(
            model_name='account',
            name='citizen_card_sn',
            field=models.CharField(default='', unique=True, max_length=256),
            preserve_default=False,
        ),
    ]
