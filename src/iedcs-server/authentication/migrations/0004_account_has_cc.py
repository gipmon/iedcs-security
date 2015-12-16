# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0003_auto_20151215_2322'),
    ]

    operations = [
        migrations.AddField(
            model_name='account',
            name='has_cc',
            field=models.BooleanField(default=False),
            preserve_default=True,
        ),
    ]
