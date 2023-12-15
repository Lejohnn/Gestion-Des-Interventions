<template>
  <div class="auth-wrapper d-flex align-center justify-center pa-4">
    <VCard class="auth-card pa-4 pt-7" max-width="448" >
      <VCardItem class="justify-center">
        <template #prepend>
          <div class="d-flex">
            <VImg :src="logo"  :width="100" />
          </div>
        </template>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="text-h5 font-weight-semibold mb-1">
          Formulaire des étudiants 👨‍🎓
        </h5>
        <p class="mb-0">
          Entrez vos informations et continuez aisement.
        </p>
      </VCardText>

      <VCardText>
        <VForm ref="formDeposit" @submit.prevent="login">
          <VRow>
            <!-- email -->
            <VCol cols="12">
              <VTextField
              prepend-inner-icon="mdi-account-badge-outline"
              v-model="username"
              :rules="[rules.required, rules.counter]"
              placeholder="nanyangbrice@gmail.com"
              autocomplete="off"
              label="Matricule"/>
            </VCol>

            <!-- password -->
            <VCol cols="12">
              <VTextField
              prepend-inner-icon="mdi-lock-percent-open-outline"
              v-model="password"
              :rules="[rules.required, rules.counter]"
              label="Code d'authentification"
              :type="isPasswordVisible ? 'text' : 'password'"
              placeholder="* * * * * * * *"
              autocomplete="off"
              :append-inner-icon="isPasswordVisible ? 'mdi-eye-off-outline' : 'mdi-eye-outline'"
              @click:append-inner="isPasswordVisible = !isPasswordVisible"/>

              <!-- remember me checkbox -->
              <div class="d-flex align-center mt-1 mb-4">
                <VCheckbox id="privacy-policy" inline />
                <VLabel for="privacy-policy" style="opacity: 1;" >
                  <span class="me-1">Souhaitez réelement continuer ?</span>
                </VLabel>
              </div>

              <!-- login button -->
              <VBtn block type="submit">
                Connexion <v-icon aria-hidden="false" class="mx-1">mdi-login</v-icon>
              </VBtn>
            </VCol>

            <VCol cols="12" class="text-center text-base" >
              <span>Je suis plutôt un  </span>
              <RouterLink class="text-primary" to="/register" >
                enseignant !
              </RouterLink>
            </VCol>

            <!-- create account -->
            
          </VRow>
        </VForm>
      </VCardText>
    </VCard>

    <VImg class="auth-footer-start-tree d-none d-md-block" :src="authV1Tree" :width="250" />
    <VImg :src="authV1Tree2" class="auth-footer-end-tree d-none d-md-block" :width="350" />
    <VImg class="auth-footer-mask d-none d-md-block" :src="authThemeMask" />
  </div>
</template>

<script>
  import AuthProvider from '@/views/pages/authentication/AuthProvider.vue'
  import { useTheme } from 'vuetify'
  import logo from '@images/logo.png'
  import authV1MaskDark from '@images/pages/auth-v1-mask-dark.png'
  import authV1MaskLight from '@images/pages/auth-v1-mask-light.png'
  import authV1Tree2 from '@images/pages/auth-v1-tree-2.png'
  import authV1Tree from '@images/pages/auth-v1-tree.png'
  import {useRouter} from "vue-router"
  import router from '@/router'

  import axios from "axios";
  import Swal from 'sweetalert2'

  export default {
    setup() {
      const vuetifyTheme = useTheme()
      const authThemeMask = computed(() => { return vuetifyTheme.global.name.value === 'light' ? authV1MaskLight : authV1MaskDark })
      const isPasswordVisible = ref(false)
      const remember = ref(false)
      return {
        vuetifyTheme,
        authThemeMask,
        isPasswordVisible,
        remember,
        authV1Tree2,
        authV1Tree,
        logo,
      }
    },

    data(){
      return {
        username: "",
        password: "",
        rules: {
          required: value => !!value || 'Renseignez absolument ce champ.',
          req: value => !!value || '',
          counter: value => value.length > 2 || 'Renseignez au moins 03 caractères',

        },
      }
    },

    created() {
      this.login();
    },

    methods: {
      async login() {
        const { valid } = await this.$refs.formDeposit.validate();
        if (valid != true) {
          Swal.fire({
            'icon' : 'error',
            'text' : "Désolé ces informations sont abscentes ou incorrectes.",
            'toast' : true,
            'position' : 'top-end',
            'showCancelButton' : false,
            'showConfirmButton' : false,
            'timer': 5000,
            'timerProgressBar': true
          });
        } 
        else {
          let response = await axios.get("allEtu");
          let data = response.data;
          console.log(data)
          let user = [];
          for (var i = 0; i < data.length; i++) {
            if (data[i].login == this.username && data[i].password == this.password) {
              user = data[i];
              break;
            }
          }
          user.created_by = "etudiant";
          console.log(user)
          
          if (user.length === 0) {
            Swal.fire({
              'icon' : 'error',
              'text' : "Désolé ces informations sont abscentes ou incorrectes.",
              'toast' : true,
              'position' : 'top-end',
              'showCancelButton' : false,
              'showConfirmButton' : false,
              'timer': 5000,
              'timerProgressBar': true
            });
          }
          else{
            localStorage.setItem("account", JSON.stringify(user));
            Swal.fire({
              'icon' : 'success',
              'text' : "Connexion établie avec succès.",
              'toast' : true,
              'position' : 'top-end',
              'showCancelButton' : false,
              'showConfirmButton' : false,
              'timer': 5000,
              'timerProgressBar': true
            });
            this.$router.push("/dashboard");
          }
        }
      },


      async redirect(){
        setTimeout( () => this.$router.push({ path: '/'}), 3);
      }
    }
  }
</script>

<style lang="scss">
  @use "@core/scss/pages/page-auth.scss";
</style>
