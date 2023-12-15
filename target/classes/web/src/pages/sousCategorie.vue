<template>
  <VRow >
    <VCol
      cols="12"
      sm="12"
      md="12"
    >
      <VExpansionPanels variant="accordion">
        <VExpansionPanel>
          <VExpansionPanelTitle> Ajouter une sous Catégorie </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VForm
              ref="addsCat"
              @submit.prevent="addsCat"
              class="mt-1 mb-1"
            >
              <VRow>
                <VCol
                  cols="12"
                  md="6"
                >
                  <VAutocomplete
                    v-model="souscategorie.categorie_id"
                    label="Catégorie"
                    density="compact"
                    persistent-placeholder
                    :items="categorieListe"
                    :rules="[rules.required]"
                    clearable
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="6"
                >
                  <VAutocomplete
                    v-model="souscategorie.departement_id"
                    label="Département"
                    density="compact"
                    persistent-placeholder
                    :items="departementList"
                    :rules="[rules.required]"
                    clearable
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="10"
                >
                  <VTextField
                    v-model="souscategorie.nom"
                    placeholder="ex : ......"
                    label="Sous catégorie"
                    density="compact"
                    persistent-placeholder
                    :rules="[rules.required]"
                    clearable
                  />
                </VCol>

                <VCol
                  cols="12"
                  md="2"
                >
                  <VBtn
                    type="submit"
                    block
                  >
                    Enregistrer
                  </VBtn>
                </VCol>
              </VRow>
            </VForm>
          </VExpansionPanelText>
        </VExpansionPanel>

        <VExpansionPanel>
          <VExpansionPanelTitle> Liste des sous Catégories </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VTextField
              v-model="search"
              class="mb-2"
              placeholder="Recherchez quelque chose ......"
              label="Search"
              density="compact"
              clearable
              persistent-placeholder
            />
            <VDataTable
              :headers="headers"
              :items="souscategorieList"
              :search="search"
              :items-per-page="5"
              density="compact"
            >
              <template #item.actions="{ item }">
                <VDialog
                  v-model="modifier"
                  persistent
                  class="v-dialog-sm"
                >
                  <!-- Dialog Activator -->
                  <template #activator="{ props }">
                    <VBtn
                      variant="text"
                      @click="open(item.raw)"
                      v-bind="props"
                    >
                      <VIcon icon="mdi-backup-outline"></VIcon>
                      <VTooltip
                        location="top"
                        activator="parent"
                      >
                        Modifier la sous catégorie {{ item.raw.nom }}
                      </VTooltip>
                    </VBtn>
                  </template>

                  <!-- Dialog Content -->
                  <VCard title="Modifier une Sous-Catégorie">
                    <VForm
                      ref="updatesCat"
                      @submit.prevent="updatesCat"
                      class="mt-1 mb-1"
                    >
                      <DialogCloseBtn
                        variant="text"
                        size="small"
                        @click="modifier = false"
                      />

                      <VCardText>
                        <VRow>
                          <VCol
                            cols="12"
                            md="12"
                          >
                            <VAutocomplete
                              v-model="categorie_id"
                              label="Catégorie"
                              density="compact"
                              persistent-placeholder
                              :items="categorieListe"
                              :rules="[rules.required]"
                              clearable
                            />
                          </VCol>

                          <VCol
                            cols="12"
                            md="12"
                          >
                            <VAutocomplete
                              v-model="departement_id"
                              label="Département"
                              density="compact"
                              persistent-placeholder
                              :items="departementList"
                              :rules="[rules.required]"
                              clearable
                            />
                          </VCol>

                          <VCol
                            cols="12"
                            md="12"
                          >
                            <VTextField
                              v-model="nom"
                              placeholder="ex : ......"
                              label="Sous catégorie"
                              density="compact"
                              persistent-placeholder
                              :rules="[rules.required]"
                              clearable
                            />
                          </VCol>
                        </VRow>
                      </VCardText>

                      <VCardActions>
                        <VSpacer />
                        <VBtn
                          color="error"
                          variant="outlined"
                          @click="modifier = false"
                        >
                          Annuler
                        </VBtn>
                        <VBtn
                          color="primary"
                          variant="outlined"
                          type="submit"
                        >
                          Modifier
                        </VBtn>
                      </VCardActions>
                    </VForm>
                  </VCard>
                </VDialog>
              </template>
            </VDataTable>
          </VExpansionPanelText>
        </VExpansionPanel>
      </VExpansionPanels>
    </VCol>
  </VRow>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'
import { VDataTable } from 'vuetify/labs/VDataTable'
import { useToast } from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-sugar.css'

export default {
  components: {
    VDataTable,
  },

  setup() {
    const search = ref('')
    const toast = useToast()
    const ajouter = ref(false)
    const modifier = ref(false)
    const headers = [
      { title: 'ID', sortable: false, key: 'id' },
      { title: 'CATEGORIE', key: 'categorie.nom' },
      { title: 'DEPARTEMENT', key: 'departement.nom' },
      { title: 'SOUS CATEGORIE', key: 'nom' },
      { title: 'ACTIONS', sortable: false, key: 'actions' },
    ]

    return {
      toast,
      search,
      ajouter,
      headers,
      modifier,
    }
  },

  data() {
    return {
      search: '',
      categorieListe: [],
      souscategorieList: [],
      departementList: [],
      id: '',
      categorie_id: '',
      departement_id: '',
      nom: '',
      student: '',
      souscategorie: {
        categorie_id: '',
        departement_id: '',
        nom: '',
      },

      datas: [
        {
          id: '',
          categorie: '',
          departement: '',
          nom: '',
        },
      ],

      rules: {
        required: value => !!value || 'Désolé mais ce champ est obligatoire.',
      },
    }
  },

  created() {
    this.getCategorie()
    this.getDepartement()
    this.getDatas()

    let account = JSON.parse(localStorage.getItem('account'))
    console.log(account)
    if (!account) {
      Swal.fire({
        icon: 'warning',
        text: 'Attention, cette connexion est illégitime.',
        toast: true,
        position: 'top-end',
        showCancelButton: false,
        showConfirmButton: false,
        timer: 5000,
        timerProgressBar: true,
      })
      this.$router.push('/login')
    }else{
      if (account.created_by != 'etudiant') {
        this.student = false;
      } 
    }
  },

  methods: {
    async getCategorie() {
      try {
        axios
          .get('allCat')
          .then(response => {
            this.categorieListe = response.data
            for (let i = 0; i < response.data.length; i++) {
              this.categorieListe[i] = response.data[i].id + '-) ' + response.data[i].nom
            }
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async getDepartement() {
      try {
        axios
          .get('allDep')
          .then(response => {
            this.departementList = response.data
            for (let i = 0; i < response.data.length; i++) {
              this.departementList[i] = response.data[i].id + '-) ' + response.data[i].nom
            }
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async getDatas() {
      try {
        axios
          .get('allsCat')
          .then(response => {
            this.souscategorieList = response.data
          })
          .catch(error => {
            console.log(error)
          })
      } catch (error) {
        console.log(error)
      }
    },

    async addsCat() {
      try {
        const valid = this.$refs.addsCat.validate()
        if (valid) {
          let categorie = this.souscategorie.categorie_id.split('-)')
          let departement = this.souscategorie.departement_id.split('-)')

          Swal.fire({
            title: 'Êtes vous sûres ?',
            html:
              'Souhaitez vous enregistré la sous catégorie <b>' +
              this.souscategorie.nom +
              '</b> respectivement lié à' +
              ' catégorie <b>' +
              categorie[1] +
              '</b> et au département <b>' +
              departement[1] +
              '</b>?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .post('addsCat/' + categorie[0] + '/' + departement[0], {
                  categorie_id: categorie[0],
                  departement_id: departement[0],
                  nom: this.souscategorie.nom,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.souscategorie.nom = ''
                  this.souscategorie.categorie_id = ''
                  this.souscategorie.departement_id = ''
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },

    async open(data) {
      this.modifier = true
      this.id = data.id
      this.nom = data.nom
      console.log(data)
    },

    async updatesCat() {
      try {
        const valid = this.$refs.updatesCat.validate()
        console.log(valid)
        if (valid) {
          this.modifier = false
          let categorie = this.categorie_id.split('-)')
          let departement = this.departement_id.split('-)')

          Swal.fire({
            title: 'Êtes vous sûres ?',
            html:
              'Souhaitez vous modifier la sous catégorie <b>' +
              this.nom +
              '</b> respectivement lié à' +
              ' catégorie <b>' +
              categorie[1] +
              '</b> et au département <b>' +
              departement[1] +
              '</b>?',
            showDenyButton: true,
            showCancelButton: false,
            confirmButtonText: 'Je le souhaite',
            denyButtonText: `Annuler`,
          }).then(result => {
            if (result.isConfirmed) {
              axios
                .put('updatesCat/' + this.id + '/' + categorie[0] + '/' + departement[0], {
                  nom: this.nom,
                })
                .then(response => {
                  this.toast.info(response.data, {
                    position: 'top-right',
                    duration: 7500,
                  })
                  this.modifier = false
                  this.getDatas()
                })
            }
          })
        } else {
          this.toast.error('Données abscentes ou incorrectes', { position: 'top-right', duration: 7500 })
        }
      } catch (error) {
        console.log(error)
      }
    },
  },
}
</script>
