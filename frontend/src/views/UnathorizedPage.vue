<template>
    <div class="container">
        <div class="page-wrap d-flex flex-row align-items-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-12 text-center">
                        <span class="display-1 d-block">
                            401
                        </span>
                        <div class="mb-4 lead">
                          Unauthorized! Access to this resourse is denied.
                        </div>
                      <router-link to="/home" class="btn btn-link"> Back to Home Page </router-link>
                      <router-link to="/login" class="btn btn-link"> Back to Login Page </router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
  name: 'unauthorized'
}
</script>

<style lang="less" scoped>

</style>
