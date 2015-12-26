from base import *

DEBUG = False
TEMPLATE_DEBUG = False

REST_FRAMEWORK = {
    'DEFAULT_RENDERER_CLASSES': (
        'rest_framework.renderers.JSONRenderer',
    )
}
